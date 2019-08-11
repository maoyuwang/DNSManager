public class NameCheap extends DNSProvider {

    NameCheap(String pubKey, String pravKey)
    {
        super(pubKey,pravKey);

    }

    @Override
    public Record[] getRecords() {

        Record[] r = new Record[2];

        r[0] = new Record("sshdyas.me","A","www","114.114.114.114");
        r[1] = new Record("sshdyas.me","A","test","23.123.122.31");
        return r;
    }

    @Override
    public boolean addRecord(Record r) {
        return true;
    }

    @Override
    public boolean deleteRecord(Record r) {
        return true;
    }

    @Override
    public boolean updateRecord(Record r) {
        return true;
    }
}
