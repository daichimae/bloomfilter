/**
 * A class that represents an IPv4 address.
 *
 * @author Daichi Mae
 */
public class IPv4Address {
    public int[] address = new int[4];
    public int subnetMask = -1; // -1 means there's no subnet mask

    /**
     * Constructor.
     *
     * @param ipAddress IPv4 address as a String of the form a.b.c.d/x. The
     *                  subnet mask part is optional.
     */
    public IPv4Address(String ipAddress) {
        String[] addressString = ipAddress.split("/")[0].split("\\.");
        for(int i = 0; i < address.length; i++) {
            address[i] = Integer.valueOf(addressString[i]);
        }
        if(ipAddress.contains("/")) {
            subnetMask = Integer.valueOf(ipAddress.split("/")[1]);
        }
    }

    /**
     * Get the prefix of an IP address using a mask.
     *
     * @param subnetMask mask to be used to get the prefix
     * @return network prefix as a IPvAddress object
     */
    public IPv4Address getPrefix(int subnetMask) {
        String addressString = address[0] + "."  + address[1] + "." + address[2]
                + "." + address[3] + "/" + subnetMask;
        IPv4Address prefix = new IPv4Address(addressString);
        int[]maskBits = {255, 255, 255, 255};

        // create a bit vector to mask an address
        int octetIndex = 3;
        for(int i = 0; i < 32 - subnetMask; i++) {
            if(i > 0 && i % 8 == 0) {
                octetIndex--;
            }
            maskBits[octetIndex] = (maskBits[octetIndex] << 1) & 255;
        }

        // apply the mask
        for(int i = 0; i < 4; i++) {
            prefix.address[i] &= maskBits[i];
        }

        return prefix;
    }

    @Override
    public boolean equals(Object that) {
        return this.toString().equals(that.toString());
    }

    @Override
    public String toString() {
        String s = address[0] + "." + address[1] + "."  + address[2] + "."
                + address[3];
        if(subnetMask >= 0) {
            s += "/" + subnetMask;
        }
        return s;
    }
}
