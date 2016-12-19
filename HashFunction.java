/**
 * A simple hash function.
 *
 * @author Daichi Mae
 */
public class HashFunction {
    private int nonce; // initialization number that's used once

    public HashFunction(int nonce) {
        this.nonce = nonce;
    }

    /**
     * Compute a hash value of an object.
     *
     * @param o Object to be hashed
     * @return Positive integer
     */
    public int hash(Object o) {
        int hashValue = 1 + nonce * nonce;

        // for each character of the String representation of the object
        for(char ch : o.toString().toCharArray()) {
            hashValue *= (int) ch;
            hashValue++;
        }
        return Math.abs(hashValue);
    }
}
