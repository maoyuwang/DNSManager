public class NameSilo extends DNSProvider {

    NameSilo(String pubKey, String pravKey)
    {
        super(pubKey,pravKey);

    }

    @Override
    public Record[] getRecords() {

        Record[] r = new Record[2];

        r[0] = new Record("wwneg461728.com","A","www","14.112.144.114");
        r[1] = new Record("wwneg461728.com","A","test","211.143.12.31");
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
