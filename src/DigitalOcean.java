import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DigitalOcean extends DNSProvider {

    private HashMap<String, String> headers ;

    DigitalOcean(String pubKey,String pravKey)
    {
        super(pubKey,pravKey);

        headers = new HashMap<String,String>();
        headers.put("User-Agent","OkHttp Headers.java");
        headers.put("Authorization",String.format("%s %s",pubKey , pravKey)  );
        headers.put("Content-Type","application/json");
    }

    public HashMap<String,String> getZones(){

        HashMap<String,String> result = new HashMap<String,String>();
        //url and getting the reuslt
        String url = "https://api.digitalocean.com/v2/domains";
        String returnStr = API.GET(url,headers);
        // 处理JSON
        JSONObject jsonObj = JSON.parseObject(returnStr);
        JSONArray zones = jsonObj.getJSONArray("domains");
        //get the reuslt
        for(int n=0; n < zones.size(); n++) {
            JSONObject zone = zones.getJSONObject(n);
            result.put( zone.get("name").toString() , zone.get("name").toString() );
        }

        return result;
    }

    public JSONObject getDomainRecords(String domain) {
        //getting all the records from one specific domain
        String url = String.format("https://api.digitalocean.com/v2/domains/%s/records", domain );
        return JSON.parseObject(API.GET(url , headers));
    }


    @Override
    public Record[] getRecords() {

        Record[] r = null;
        ArrayList<Record> Rlst = new ArrayList<Record>();
        //getting all the domains
        String[] domains = (String [])this.getZones().keySet().toArray();
        //from all the domains, get all the records
        for(int n=0; n < domains.length ; n++) {
            JSONObject jsonObj = getDomainRecords( domains[n] );
            JSONArray jsonArray = jsonObj.getJSONArray("domain_records");

            for(int i=0; i< jsonArray.size(); i++){
                JSONObject record = jsonArray.getJSONObject(i);

                //TODO: Make sure the domains->match what we want
                String Rdomain = domains[n];
                String Rtype = record.get("type").toString();
                String Rname = record.get("name").toString();
                String Rvalue = record.get("data").toString();

                Rlst.add(new Record(Rdomain, Rtype, Rname, Rvalue));
            }

        }

        return (Record[])Rlst.toArray() ;
    }

    @Override
    public boolean addRecord(Record r) {

        String url = String.format("https://api.digitalocean.com/v2/domains/%s/records" , r.domain);

        JSONObject newRecord = new JSONObject();
        newRecord.put("type", r.type);
        newRecord.put("name", r.name);
        newRecord.put("data", r.value);
        newRecord.put("priority", null);
        newRecord.put("port", null );
        newRecord.put("ttl", 1800);
        newRecord.put("weight",null);
        newRecord.put("flags", null);
        newRecord.put("tag", null);

        API.POST(url, headers, newRecord);
        return true;
    }

    @Override
    public boolean deleteRecord(Record r) {
        JSONObject jsonObj = getDomainRecords(r.domain);
        JSONArray jsonArray = jsonObj.getJSONArray("domain_records");
        String recordid = null;
        String url = null;
        for(int i=0; i< jsonArray.size(); i++) {
            JSONObject record = jsonArray.getJSONObject(i);

            if(record.get("type").toString().equals(r.type) && record.get("name").toString().equals(r.name) && record.get("value").toString().equals(r.value) )
            {
                recordid = record.getString("id");
            }
        }

        url = String.format("https://api.digitalocean.com/v2/domains/%s/records/%s", r.domain , recordid);
        API.DELETE(url, headers , null);

        return true;
    }

    @Override
    public boolean updateRecord(Record r) {
        JSONObject jsonObj = getDomainRecords(r.domain);
        JSONArray jsonArray = jsonObj.getJSONArray("domain_records");
        String recordid = null;
        String url = null;
        for(int i=0; i< jsonArray.size(); i++) {
            JSONObject record = jsonArray.getJSONObject(i);

            if(record.get("type").toString().equals(r.type) && record.get("name").toString().equals(r.name) && record.get("value").toString().equals(r.value) )
            {
                recordid = record.getString("id");
            }
        }

        JSONObject updateObj = new JSONObject();
        updateObj.put("name", r.name);

        url = String.format("https://api.digitalocean.com/v2/domains/%s/records/%s",r.domain , recordid);
        API.PUT(url, headers, updateObj);

        return true;
    }
}
