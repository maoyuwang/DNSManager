public class GoDaddy extends DNSProvider {

    GoDaddy(String pubKey, String pravKey)
    {
        super(pubKey,pravKey);

    }

    @Override
    public Record[] getRecords() {

        Record[] r = new Record[8];
        System.out.println("System.out           hello");
        r[0] = new Record("function.club","A","www","114.114.114.114");
        r[1] = new Record("function.club","A","test","23.123.122.31");
        r[2] = new Record("function.club","A","myblog","23.14.53.12");
        r[3] = new Record("function.club","CNAME","direct","qq.com");
        r[4] = new Record("function.blog","A","www","21.234.123.4");
        r[5] = new Record("function.blog","TEXT","vary1","TEST TEST Records");
        r[6] = new Record("function.blog","CNAME","gg","www.qqdie.com");
        r[7] = new Record("function.blog","AAAA","ipv6","2620:0:2820:2210:70a:77c2:d184:76b9");
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
