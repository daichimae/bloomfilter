import java.util.ArrayList;
import java.util.List;

/**
 * A routing table that uses Bloom filters for a lookup.
 *
 * @author Daichi Mae
 */
public class RoutingTable {
    private final int addressLength = 32; // IPv4

    private HashTable[] routingTable = new HashTable[addressLength];
    private BloomFilter[] bloomFilters = new BloomFilter[addressLength];

    // Bloom filter parameters
    private final int k = 10; // the number of hash functions
    private final int m = 40000; // the length of the counter vector


    public RoutingTable() {
        for(int i = 0; i < addressLength; i++) {
            routingTable[i] = new HashTable();
            bloomFilters[i] = new BloomFilter(k, m);
        }
    }

    /**
     * Add an entry to the routing table.
     *
     * @param destinationString destination address of the form a.b.c.d/e as a String.
     * @param nextHopString IP address of the next hop of the form a.b.c.d as a String.
     */
    public void addEntry(String destinationString, String nextHopString) {
        IPv4Address destination = new IPv4Address(destinationString);
        IPv4Address nextHop = new IPv4Address(nextHopString);

        routingTable[destination.subnetMask - 1].put(destination, nextHop);
        bloomFilters[destination.subnetMask - 1].add(destination);
    }

    /**
     * Given an destination address, return the longest prefix using Bloom
     * filters.
     *
     * @param destinationString address to be queried
     * @return the longest prefix
     */
    public String lookup(String destinationString) {
        IPv4Address destination = new IPv4Address(destinationString);
        List<Integer> matchVector = new ArrayList<>();

        // configure the match vector
        for(int i = addressLength - 1; i >= 0; i--) {
            if (bloomFilters[i].contains(destination.getPrefix(i + 1))) {
                matchVector.add(i);
            }
        }

        // look for a match
        for(int index : matchVector) {
            IPv4Address prefix = destination.getPrefix(index + 1);
            IPv4Address nextHop = (IPv4Address) routingTable[index].get(prefix);
            if(nextHop != null) {
                return destinationString + "  -->  " + prefix;
            }
        }
        return destinationString + "  -->  " + "No match";
    }
}
