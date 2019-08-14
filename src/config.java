
public class config {
	private String publicKey,privateKey, name;

    public config(String name, String publicKey, String privateKey)
    {
    	this.name = name;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }
    
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
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
