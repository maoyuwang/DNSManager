import com.alibaba.fastjson.*;
import com.squareup.okhttp.*;
import java.util.*;
import java.io.*;
import okio.*;

/**
 * Represents a Client to CloudFlare Service.
 */
public class CloudFlare extends DNSProvider {
    private HashMap<String, String> headers ;

    /**
     * Initialize a CloudFlare Client with given authentication information.
     * @param pubKey    The public key provided by CloudFlare.
     * @param privKey   The private key provided by CloudFlare.
     */
    public CloudFlare(String pubKey, String privKey) {
        super(pubKey, privKey);
        headers = new HashMap<String,String>();
        headers.put("User-Agent","OkHttp Headers.java");
        headers.put("X-Auth-Email",getPublicKey());
        headers.put("X-Auth-Key",getPrivateKey());
        headers.put("Content-Type","application/json");


        //getRecords<-----TODO: zone_id - record_id
    }

    /**
     * Get all the zones information from CloudFlare.
     * @return  A map that maps (zoneid:domain) relationship.
     */
    public HashMap<String,String> getZones()
    {
        HashMap<String,String> result = null;

        String url = "https://api.cloudflare.com/client/v4/zones?page=1&per_page=10&order=type&direction=asc";

        String returnStr = API.GET(url,headers);

        JSONObject jsonObj = JSON.parseObject(returnStr);
        JSONArray zones = jsonObj.getJSONArray("result");



        result = new HashMap<String,String>();

        for (int i = 0 ; i < zones.size(); i++)
        {
            JSONObject zone = zones.getJSONObject(i);
            String zoneID = zone.get("id").toString();
            String domain = zone.get("name").toString();
            result.put(domain,zoneID);
        }


        return result;
    }

    /**
     * Get All records from the domain.
     * @param domain    The domain to get info.
     * @return  The JSON object that contains the information of the given domain.
     */
    public JSONObject getDomainRecords(String domain) {
        String url = String.format("https://api.cloudflare.com/client/v4/zones/%s/dns_records", this.getZones().get(domain) );
        return JSON.parseObject(API.GET(url , headers));
    }

    /**
     * Get All records of all domains of this CloudFlare account.
     * @return  The array of all the Records of all domains owned by this CloudFlare account.
     */
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
            JSONArray records = jsonObj.getJSONArray("result");

            for(int n=0 ; n < records.size() ; n++) {
                JSONObject record = records.getJSONObject(n);

                if (!record.get("type").toString().equals("MX")) {

                    String RecordDomain = record.get("zone_name").toString();
                    String RecordType = record.get("type").toString();
                    String RecordName = record.get("name").toString();
                    String RecordValue = record.get("content").toString();

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

    /**
     * Add a new record.
     * @param r The record to add.
     * @return  true or false if this operation is successful.
     */
    @Override
    public boolean addRecord(Record r) {
        //url
        String url = String.format("https://api.cloudflare.com/client/v4/zones/%s/dns_records", this.getZones().get(r.domain) );

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

    /**
     * Delete a given record.
     * @param r The record to delete.
     * @return  true or false if this delete action is finished successfully.
     */
    @Override
    public boolean deleteRecord(Record r) {
        //get all the Records in the Zone and initializer
        String zoneid = this.getZones().get(r.domain);
        JSONObject jsonObj = this.getDomainRecords( r.domain );
        JSONArray records = jsonObj.getJSONArray("result");
        String url = "https://api.cloudflare.com/client/v4/zones/%s/dns_records/%s";


        // iterate all the zones to find out which specific zone we need to delete
        for(int n=0 ; n < records.size() ; n++) {
            JSONObject record = records.getJSONObject(n);

            if( r.name.equals(record.getString("name").split("\\.")[0] ) ) {
                String recordid = record.get("id").toString();
                url = String.format(url, zoneid, recordid);
                System.out.println(API.DELETE(url, headers, new JSONObject()));

                //TODO: for Delete we don't need any new JSONObject
                break;
            }
        }
            return true;
    }

    /**
     * update a given record.
     * @param r The record to update.
     * @return  true or false if this delete action is finished successfully.
     */
    @Override
    public boolean updateRecord(Record r) {
        //get all the Records in the Zone and initializer
        String zoneid = this.getZones().get(r.domain);
        String recordid = null;
        String url = "https://api.cloudflare.com/client/v4/zones/%s/dns_records/%s" ;

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
        updateObj.put("type", r.type);
        updateObj.put("name", r.name);
        updateObj.put("content", r.value);
        updateObj.put("ttl", 1);
        updateObj.put("proxied",false);


        API.PUT(url, headers, updateObj);


        return true;
    }


}
