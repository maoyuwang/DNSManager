import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NameSilo extends DNSProvider {
    private HashMap<String, String> headers ;

    NameSilo(String pubKey, String pravKey)
    {
        super(pubKey,pravKey);
        headers = new HashMap<String,String>();

    }

    public HashMap<String,String> getZones()
    {
        HashMap<String,String> result = null;

        String url = String.format("https://www.namesilo.com/api/listDomains?version=1&type=xml&key=%s",this.getPrivateKey() );

        String returnStr = API.GET(url,headers);

        JSONObject jsonObj = JSON.parseObject(returnStr);
        JSONArray zones = jsonObj.getJSONArray("domains");

        result = new HashMap<String,String>();

        for (int i = 0 ; i < zones.size(); i++)
        {
            JSONObject zone = zones.getJSONObject(i);
            String zoneID = zone.get("domain").toString();
            String domain = zone.get("domain").toString();
            result.put(domain,zoneID);
        }


        return result;
    }

    public JSONObject getDomainRecords(String domain) {
        String url = String.format("https://www.namesilo.com/api/listDomains?version=1&type=json&key=%s&domain=%s",this.getPrivateKey() ,this.getZones().get(domain) );
        return JSON.parseObject(API.GET(url , headers));
    }

    @Override
    public Record[] getRecords() {
        //get all zones we have & Initialize
        HashMap<String,String> ZoneMap = this.getZones();
        ArrayList<Record> RecordList = new ArrayList<Record>();
        Record[] result = null ;

        //location-setting
        for( Map.Entry<String, String> entryitr : ZoneMap.entrySet() ) {
            String domain = entryitr.getKey();

            JSONObject jsonObj = this.getDomainRecords(domain);
            JSONArray records = jsonObj.getJSONArray("reply");

            for(int n=0 ; n < records.size() ; n++) {
                JSONObject record = records.getJSONObject(n);

                if (!record.get("type").toString().equals("MX")) {

                    String RecordDomain = record.get("zone_name").toString();
                    String RecordType = record.get("type").toString();
                    String RecordName = record.get("record_id").toString();
                    String RecordValue = record.get("value").toString();

                    String[] name_lst = RecordName.split("\\.");
                    if (name_lst.length != 2) {
                        RecordName = name_lst[0];
                    }

                    RecordList.add(new Record(RecordDomain, RecordType, RecordName, RecordValue));
                }
            }
        }
        result = new Record[RecordList.size()];
        for (int i = 0 ; i < RecordList.size(); i++)
        {
            result[i] = RecordList.get(i);
        }
        return result;


    }

    @Override
    public boolean addRecord(Record r) {
        //url
        String url = String.format("ttps://www.namesilo.com/api/dnsSecAddRecord?version=1&type=xml&key=%s&domain=%s", this.getPrivateKey() ,this.getZones().get(r.domain) );

        //json object- the details of new Record
        JSONObject newrecord = new JSONObject();
        newrecord.put("type" , r.type );
        newrecord.put("name", r.name) ;
        newrecord.put("content", r.value);
        newrecord.put("ttl", 1);
        newrecord.put("priority","10");
        newrecord.put("proxied", false);

        String returnStr = null ;
        //post
        try
        {
            returnStr = API.POST(url , headers, newrecord);
        } catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }

        //TODO: make returnStr -> JSONObject and check does it return success;
        System.out.println(returnStr);
        return true;
    }

    @Override
    public boolean deleteRecord(Record r) {
        //get all the Records in the Zone and initializer
        String zoneid = this.getZones().get(r.domain);
        JSONObject jsonObj = this.getDomainRecords( r.domain );
        JSONArray records = jsonObj.getJSONArray("result");
        String url = String.format("https://www.namesilo.com/api/dnsDeleteRecord?version=1&type=json&key=%s&domain=namesilo.com",this.getPrivateKey());


        // iterate all the zones to find out which specific zone we need to delete
        for(int n=0 ; n < records.size() ; n++) {
            JSONObject record = records.getJSONObject(n);

            if( r.name.equals(record.getString("name").split("\\.")[0] ) ) {
                String recordid = record.get("id").toString();
                url = String.format(url, zoneid, recordid);
                System.out.println(API.DELETE(url, headers, new JSONObject()));
                break;
            }
        }
        return true;
    }

    @Override
    public boolean updateRecord(Record r) {
        //get all the Records in the Zone and initializer
        String zoneid = this.getZones().get(r.domain);
        String recordid = null;
        String url = String.format( "https://www.namesilo.com/api/dnsUpdateRecord?version=1&type=json&key=%s&domain=%s", this.getPrivateKey(), r.domain ) ;

        //finding the specific zoneid and recordid
        JSONObject jsonObj = this.getDomainRecords( r.domain );
        JSONArray records = jsonObj.getJSONArray("result");

        //find the recordid
        for(int n=0 ; n < records.size() ; n++) {
            JSONObject record = records.getJSONObject(n);

            if (r.name.equals(record.getString("name").split("\\.")[0])) {
                recordid = record.get("id").toString();
                break;
            }
        }

        url = String.format(url,zoneid,recordid);


        //create the update json object for the PUT
        JSONObject updateObj = new JSONObject();
        API.PUT(url, headers, updateObj);


        return true;
    }
}
