public class Record {
    public String type;
    public String name;
    public String value;

    public Record(String type,String name, String value)
    {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        super.clone();
        return new Record(type,name,value);
    }
}
