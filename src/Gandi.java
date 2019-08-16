import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A Client for calling Gandi API
 */
public class Gandi extends DNSProvider {
    private HashMap<String, String> headers ;

    /**
     * Initialize a Gandi Client with given authentication information.
     * @param pubKey    The public key provided by CloudFlare.
     * @param pravKey   The private key provided by CloudFlare.
     */
    Gandi(String pubKey,String pravKey)
    {
        super(pubKey,pravKey);
        headers = new HashMap<String,String>();
        headers.put("authorization",String.format("%s %s",pubKey, pravKey) );

    }

    /**
     * Get all the zones information from Gandi.
     * @return  A map that maps (zoneid:domain) relationship.
     */
    public HashMap<String,String> getZones()
    {
        HashMap<String,String> result = null;

        // 定义地址
        String url = "https://api.gandi.net:0/v5/domain/domains";

        // 调用得到结果
        String returnStr = API.GET(url,headers);

        // 处理JSON
        JSONArray zones = JSON.parseArray(returnStr );



        result = new HashMap<String,String>();

//        System.out.println( returnStr );

        for (int i = 0 ; i < zones.size(); i++)
        {
            JSONObject zone = zones.getJSONObject(i);
            String zoneID = zone.get("id").toString();
            String domain = zone.get("fqdn_unicode").toString();
            result.put(domain,zoneID);
        }


        return result;
    }

    /**
     * Get All records from the domain.
     * @param domain    The domain to get info.
     * @return  The JSON object that contains the information of the given domain.
     */
    public JSONArray getDomainRecords(String domain) {
        String url = String.format("https://api.gandi.net:0/v5/domain/domains/%s/hosts", domain );
        return JSON.parseArray(API.GET(url , headers));
    }


    /**
     * Get All records of all domains of this account.
     * @return  The array of all the Records of all domains owned by this account.
     */
    @Override
    public Record[] getRecords() {

        HashMap<String,String> ZoneMap = this.getZones();
        ArrayList<Record> RecordList = new ArrayList<Record>();
        Record[] result = null ;


        for( Map.Entry<String, String> entryitr : ZoneMap.entrySet() ) {
            String domain = entryitr.getKey();

            JSONArray records = this.getDomainRecords(domain);

            for(int n=0 ; n < records.size() ; n++) {
                JSONObject record = records.getJSONObject(n);

                if (!record.get("type").toString().equals("MX")) {

                    String RecordDomain = record.get("fqdn").toString();
                    String RecordName = record.get("name").toString();
                    String RecordValue = record.get("ips").toString();

                    String[] name_lst = RecordName.split("\\.");
                    if (name_lst.length != 2) {
                        RecordName = name_lst[0];
                    }

                    RecordList.add(new Record(RecordDomain, "", RecordName, RecordValue));
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
        String url = String.format(" https://api.gandi.net:0/v5/domain/domains/%s/hosts", r.domain );

        //json object- the details of new Record
        JSONObject newrecord = new JSONObject();

        newrecord.put("name", r.name) ;
        newrecord.put("ips", r.value );

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
        String zoneid = this.getZones().get(r.domain);

        JSONArray records = this.getDomainRecords( r.domain );
        String url = "https://api.gandi.net:0/v5/domain/domains/%s/hosts/%s";

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
        String url = "https://api.gandi.net:0/v5/domain/domains/%s/hosts/%s" ;

        //finding the specific zoneid and recordid

        JSONArray records = this.getDomainRecords( r.domain );

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
        updateObj.put("ips", r.value);
        API.PUT(url, headers, updateObj);
        return true;
    }

}

