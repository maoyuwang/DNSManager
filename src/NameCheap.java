import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * An API Client for NameCheap.
 */
public class NameCheap extends DNSProvider {
    private HashMap<String, String> headers ;

    /**
     * Initialize a NameCheap Client with given authentication information.
     * @param pubKey    The public key provided by NameCheap.
     * @param pravKey   The private key provided by NameCheap.
     */
    NameCheap(String pubKey, String pravKey)
    {
        super(pubKey,pravKey);
        headers = new HashMap<String,String>();


    }

    /**
     * Get all the zones information from NameCheap.
     * @return  A map that maps (zoneid:domain) relationship.
     */
    public HashMap<String,String> getZones()
    {
        HashMap<String,String> result = null;

        //define the location
        String url = String.format("https://api.namecheap.com/json.response?ApiUser=%s&ApiKey=%s&UserName=%s&Command=namecheap.domains.getList&ClientIp=127.0.0.1",this.getPublicKey(), this.getPrivateKey(), this.getPublicKey() );

        // 调用得到结果
        String returnStr = API.GET(url,headers);

        // 处理JSON
        JSONArray zones = JSON.parseArray(returnStr );



        result = new HashMap<String,String>();

//        System.out.println( returnStr );

        for (int i = 0 ; i < zones.size(); i++)
        {
            JSONObject zone = zones.getJSONObject(i);
            String zoneID = zone.get("Name").toString();
            String domain = zone.get("ID").toString();
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
        String url = String.format("https://api.namecheap.com/xml.response?ApiUser=%s&ApiKey=%s&UserName=%s&Command=namecheap.domains.dns.getList&ClientIp=127.0.0.1&SLD=domain&TLD=com", domain );
        return JSON.parseArray(API.GET(url , headers));
    }

    /**
     * Get All records of all domains of this NameCheap account.
     * @return  The array of all the Records of all domains owned by this NameCheap account.
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

                    String RecordDomain = record.get("domian").toString();
                    String RecordName = record.get("name").toString();
                    String RecordValue = record.get("ip").toString();

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
        String url = String.format("ttps://api.namecheap.com/xml.response?ApiUser=%s&ApiKey=%s&UserName=%s&Command=namecheap.users.address.create", r.domain, r.value , r.type );
        //json object- the details of new Record
        JSONObject newrecord = new JSONObject();

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
        String url = "https://api.namecheap.com/json.response?ApiUser=%s&ApiKey=%sCommand=namecheap.users.address.delete";

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
        String url = "https://api.namecheap.com/xml.response?ApiUser=%s&ApiKey=%s&UserName=%s&Command=namecheap.users.address.update" ;

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
        url = String.format(url, this.getPublicKey(), this.getPrivateKey(), r.domain);
        API.PUT(url, headers, updateObj);
        return true;
    }


}
