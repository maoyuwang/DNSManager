public class NameCom extends DNSProvider {

    NameCom(String pubKey, String pravKey)
    {
        super(pubKey,pravKey);

    }

    @Override
    public Record[] getRecords() {

        Record[] r = new Record[4];

        r[0] = new Record("v2ray.fun","A","www","12.3.2.331");
        r[1] = new Record("v2ray.fun","TEXT","vary1","WEDASD-sadsaDWASD");
        r[2] = new Record("v2ray.fun","CNAME","gg","www.goog.com");
        r[3] = new Record("v2ray.fun","AAAA","ipv6","2300:0:2820:2110:70a:78c2:d184:76b9");
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
