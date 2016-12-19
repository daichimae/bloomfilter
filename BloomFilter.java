/**
 * A bloom filter implementation.
 *
 * @author Daichi Mae
 */
public class BloomFilter {
    private HashFunction[] hashFunctions;
    private int[] counters; // use counters instead of a bit vector


    /**
     * Constructor
     *
     * @param k number of hash functions
     * @param m length of the counter vector
     */
    public BloomFilter(int k, int m) {
        hashFunctions = new HashFunction[k];
        counters = new int[m];

        for(int i = 0; i < k; i++) {
            // use the index as a nonce
            hashFunctions[i] = new HashFunction(i);
        }
    }

    /**
     * Register an object by computing k hash values and incrementing the
     * corresponding counters.
     *
     * @param o object to be registered
     */
    public void add(Object o) {
        for(HashFunction f : hashFunctions) {
            counters[f.hash(o) % counters.length]++;
        }
    }

    /**
     * Check the membership of an object.
     *
     * @param o object to be checked its membership
     * @return boolean value
     */
    public boolean contains(Object o) {
        for(HashFunction f : hashFunctions) {
            if(counters[f.hash(o) % counters.length] <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return the content of the counters as a String.
     */
    @Override
    public String toString() {
        String result = "";
        for(int i = 0; i < counters.length; i++) {
            result += "counter[" + i + "] = " + counters[i] + "\n";
        }
        return result.trim();
    }
}
