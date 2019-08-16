import java.util.*;

/**
 * A DNS provider represents a Client to communicate with the DNS Servers.
 */
public abstract class DNSProvider {
    private String publicKey,privateKey, name;

    /**
     * Construct a DNSProvider Client
     * @param publicKey The publicKey which will be used for authentication usage.
     * @param privateKey The privateKey which will be used for authentication usage.
     */
    public DNSProvider(String publicKey, String privateKey)
    {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    /**
     * Get and return all records of all domains for this Provider.
     * @return  array of all records of all domains for this Provider.
     */
    public abstract Record[] getRecords();

    /**
     * Add a record to this provider.
     * @param r The record to be added to this account.
     * @return  true of false if this addition is successfully.
     */
    public abstract boolean addRecord(Record r);

    /**
     * delete a record.
     * @param r The record to be deleted to this account.
     * @return  true of false if this deletion is successfully.
     */
    public abstract boolean deleteRecord(Record r);

    /**
     * update a record.
     * @param r The record to be updated to this account.
     * @return  true of false if this action is successfully.
     */
    public abstract boolean updateRecord(Record r);

    /**
     * Getter for publicKey
     * @return publicKey
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * Setter for publickey
     * @param publicKey new publickey to be set.
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * Getter for Privatekey.
     * @return Privatekey
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * Setter for Privatekey.
     * @param privateKey    The new Privatekey.
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
