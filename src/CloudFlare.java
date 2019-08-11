import com.alibaba.fastjson.*;
import com.squareup.okhttp.*;
import java.util.*;
import java.io.*;
import okio.*;

public class CloudFlare extends DNSProvider {
    public CloudFlare(String pubKey, String privKey) {
        super(pubKey, privKey);
    }

    public HashMap<String,String> getZones()
    {
        HashMap<String,String> result = null;

        // 定义地址
        String url = "https://api.cloudflare.com/client/v4/zones?page=1&per_page=10&order=type&direction=asc";

        // 设置 Headers
        HashMap<String,String> headers = new HashMap<String,String>();
        headers.put("User-Agent","OkHttp Headers.java");
        headers.put("X-Auth-Email",getPublicKey());
        headers.put("X-Auth-Key",getPrivateKey());
        headers.put("Content-Type","application/json");

        // 调用得到结果
        String returnStr = API.GET(url,headers);

        // 处理JSON
        JSONObject jsonObj = JSON.parseObject(returnStr);
        JSONArray zones = jsonObj.getJSONArray("result");

        result = new HashMap<String,String>();

//        System.out.println( returnStr );

        for (int i = 0 ; i < zones.size(); i++)
        {
            JSONObject zone = zones.getJSONObject(i);
            String zoneID = zone.get("id").toString();
            String domain = zone.get("name").toString();
            result.put(domain,zoneID);
        }


        return result;
    }

    @Override
    public Record[] getRecords() {

        //get all zones we have
        HashMap<String,String> ZoneMap = this.getZones();

        //location-setting
        for( Map.Entry<String, String> entryitr : ZoneMap.entrySet() ) {
            String domain = entryitr.getKey();
            String zone = entryitr.getValue();

            String url = String.format("https://api.cloudflare.com/client/v4/zones/%s/dns_records", zone );

            //settign headers
            HashMap<String,String> headers = new HashMap<String,String>();
            headers.put("User-Agent","OkHttp Headers.java");
            headers.put("X-Auth-Email",getPublicKey());
            headers.put("X-Auth-Key",getPrivateKey());
            headers.put("Content-Type","application/json");

            //get results
            String returnStr = API.GET(url , headers);

            //parse and get information form the json string
            JSONObject jsonObj = JSON.parseObject(returnStr);


        }


        return null;
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

    public static void main(String[] args) {
        CloudFlare cf = new CloudFlare("wang.maoyu@hotmail.com","4c8394457166831f3d80fc7c98d9ad4a02ee1");
        cf.getRecords();
    }
}
