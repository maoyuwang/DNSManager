public class Gandi extends DNSProvider {

    Gandi(String pubKey,String pravKey)
    {
        super(pubKey,pravKey);

    }

    @Override
    public Record[] getRecords() {

        Record[] r = new Record[8];

        r[0] = new Record("myblog.me","A","www","114.114.114.114");
        r[1] = new Record("myblog.me","A","test","23.123.122.31");
        r[2] = new Record("myblog.me","A","myblog","23.14.53.12");
        r[3] = new Record("myblog.me","CNAME","direct","qq.com");
        r[4] = new Record("qqdie.com","A","www","21.234.123.4");
        r[5] = new Record("qqdie.me","TEXT","vary1","TEST TEST Records");
        r[6] = new Record("qqdie.com","CNAME","gg","www.qqdie.com");
        r[7] = new Record("qqdie.com","AAAA","ipv6","2620:0:2820:2210:70a:77c2:d184:76b9");
        return r;
    }

    @Override
    public void addRecord(Record r) {

    }

    @Override
    public void deleteRecord(Record r) {

    }

    @Override
    public void updateRecord(Record r) {

    }
}
