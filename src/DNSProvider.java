import java.util.*;

public abstract class DNSProvider {
    private String publicKey,privateKey;

    public DNSProvider(String publicKey, String privateKey)
    {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public abstract Record[] getRecords();
    public abstract boolean addRecord(Record r);
    public abstract boolean deleteRecord(Record r);
    public abstract boolean updateRecord(Record r);

    //Setter and Getter
    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
