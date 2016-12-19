import java.util.LinkedList;

/**
 * A hash table using separate chaining with linked lists. Each node in a chain
 * has an array whose first element has a key and second element has a value.
 */
public class HashTable {
    private class Chain extends LinkedList<Object[]> {}

    private HashFunction hashFunction = new HashFunction(31);

    private final int tableSize = 50000;
    private Chain[] table = new Chain[tableSize];

    public HashTable() {
        for(int i = 0; i < table.length; i++) {
            table[i] = new Chain();
        }
    }

    /**
     * Maps the specified key to the specified value in this hash table.
     *
     * @param k key
     * @param v value
     */
    public void put(Object k, Object v) {
        Object[] nodeData = {k, v};
        table[hashFunction.hash(k) % tableSize].add(nodeData);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param k key
     * @return corresponding value
     */
    public Object get(Object k) {
        Chain chain =table[hashFunction.hash(k) % tableSize];
        for(Object[] node : chain) {
            if(k.equals(node[0])) {
                return node[1];
            }
        }
        return null;
    }

    /**
     * Removes the key and its corresponding value from this table.
     *
     * @param k key
     */
    public void remove(Object k) {
        Chain chain =table[hashFunction.hash(k) % tableSize];
        for(Object[] node : chain) {
            if(k.equals(node[0])) {
                chain.remove(node);
            }
        }
    }
}
