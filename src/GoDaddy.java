import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class GoDaddy extends DNSProvider {
    private HashMap<String, String> headers;

    GoDaddy(String pubKey, String pravKey)
    {
        super(pubKey,pravKey);

        headers = new HashMap<String,String>();
        headers.put("Users",String.format("%s %s",pubKey , pravKey)  );
        headers.put("Content-Type","application/json");

    }

    /**
     * Get all zones infomation from DigitalOcean.
     * @return  A map contains all domains.
     */
    public HashMap<String,String> getZones(){

        HashMap<String,String> result = new HashMap<String,String>();
        //url and getting the reuslt
        String url = "https://api.GoDaddy.com/domains";
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

    /**
     * Get and return the records of a given domain.
     * @param domain    The domain to get all records.
     * @return  A JSON object that contains record information of the given domain.
     */
    public JSONObject getDomainRecords(String domain) {
        //getting all the records from one specific domain
        String url = String.format("https://api.Godaddy.com/domains/%s/records", domain );
        return JSON.parseObject(API.GET(url , headers));
    }

    /**
     * Get and return all records of all domains in this DigitalOcean Account.
     * @return  All the records information.
     */
    @Override
    public Record[] getRecords() {

        Record[] result = null;
        ArrayList<Record> Rlst = new ArrayList<Record>();
        //getting all the domains

        Set<String> zones = getZones().keySet();

        String[] domains = new String[zones.size()];
        Iterator<String> it = zones.iterator();
        int domainsIndex = 0;

        while (it.hasNext())
        {
            domains[domainsIndex++] = it.next();
        }


        //from all the domains, get all the records
        for(int n=0; n < domains.length ; n++) {
            JSONObject jsonObj = getDomainRecords( domains[n] );
            JSONArray jsonArray = jsonObj.getJSONArray("domain");

            for(int i=0; i< jsonArray.size(); i++){
                JSONObject record = jsonArray.getJSONObject(i);

                //TODO: Make sure the domains->match what we want
                String Rdomain = domains[n];
                String Rtype = record.get("type").toString();
                String Rname = record.get("name").toString();
                String Rvalue = record.get("ip").toString();

                Rlst.add(new Record(Rdomain, Rtype, Rname, Rvalue));
            }

        }

        result = new Record[Rlst.size()];

        for (int i = 0 ; i < Rlst.size(); i ++)
        {
            result[i] = Rlst.get(i);
        }

        return result ;
    }

    /**
     * Add a specific record to this account.
     * @param r The record to add.
     * @return  true of false if this addition is successfully.
     */
    @Override
    public boolean addRecord(Record r) {

        String url = String.format("https://api.GoDaddy.com/domains/%s/records" , r.domain);

        JSONObject newRecord = new JSONObject();
        newRecord.put("type", r.type);
        newRecord.put("name", r.name);
        newRecord.put("data", r.value);

        API.POST(url, headers, newRecord);
        return true;
    }

    /**
     * Delete a specific record from this account.
     * @param r The record to delete.
     * @return  true of false if this deletion is successfully.
     */
    @Override
    public boolean deleteRecord(Record r) {
        JSONObject jsonObj = getDomainRecords(r.domain);
        JSONArray jsonArray = jsonObj.getJSONArray("domain");
        String recordid = null;
        String url = null;
        for(int i=0; i< jsonArray.size(); i++) {
            JSONObject record = jsonArray.getJSONObject(i);

            if( record.get("name").toString().equals(r.name) )
            {
                recordid = record.getString("id");
            }
        }

        url = String.format("https://api.GoDaddy.com/domains/%s/records/%s", r.domain , recordid);
        API.DELETE(url, headers , new JSONObject());

        return true;
    }

    /**
     * Update a given record in this account.
     * @param r The new record to be updated.
     * @return  true of false if this action is successfully.
     */
    @Override
    public boolean updateRecord(Record r) {
        JSONObject jsonObj = getDomainRecords(r.domain);
        JSONArray jsonArray = jsonObj.getJSONArray("domain");
        String recordid = null;
        String url = null;
        for(int i=0; i< jsonArray.size(); i++) {
            JSONObject record = jsonArray.getJSONObject(i);
            System.out.println(record.toJSONString());

            if(record.get("name").toString().equals(r.name))
            {
                recordid = record.getString("id");
            }
        }

        JSONObject updateObj = new JSONObject();
        updateObj.put("data", r.value);

        url = String.format("https://api.GoDaddy.com/domains/%s/records/%s",r.domain , recordid);
        API.PUT(url, headers, updateObj);

        return true;
    }
}
