/**
 * A config class contains a DNSProvider's name and its authentication information.
 */
public class config {
	private String publicKey,privateKey, name;

    /**
     * Construct a config for a DNS Provider
     * @param name  The name of the DNSProvider
     * @param publicKey The Public Key of this Provider
     * @param privateKey    The Private Key of this Provider.
     */
    public config(String name, String publicKey, String privateKey)
    {
    	this.name = name;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    /**
     * Getter for publicKey
      * @return publicKey
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * Setter for publicKey
     * @param publicKey the new publicKey
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * Getter for privateKey
     * @return  The privateKey
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * Setter for privateKey.
     * @param privateKey    The new privateKey
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * Getter for name.
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

}
