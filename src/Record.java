/**
 * class includes information for each record
 * @author rwu
 *
 */
public class Record {
    public String domain;
    public String type;
    public String name;
    public String value;

    /**
     * constructor
     * @param domian domain of the record
     * @param type type of the record
     * @param name name of the record
     * @param value  value of the record
     */
    public Record(String domian, String type,String name, String value)
    {
        this.domain = domian;
        this.type = type;
        this.name = name;
        this.value = value;
    }

    /**
     * create the new record
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        super.clone();
        return new Record(domain,type,name,value);
    }


    /**
     * include all information to string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DOMAIN: " + domain +"\t");
        builder.append("TYPE: " + type + "\t");
        builder.append("NAME: " + name + "\t");
        builder.append("VALUE: " + value + "\n");

        return builder.toString();
    }
}
