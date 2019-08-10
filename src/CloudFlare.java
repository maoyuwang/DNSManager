import com.alibaba.fastjson.*;
import com.squareup.okhttp.*;
import java.util.*;
import java.io.*;
import okio.*;

public class CloudFlare extends DNSProvider {
    public CloudFlare(String pubKey, String privKey) {
        super(pubKey, privKey);
    }

    public String[] getZones()
    {
        String[] result = null;

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

        result = new String[zones.size()];

        for (int i = 0 ; i < zones.size(); i++)
        {
            JSONObject zone = zones.getJSONObject(i);
            String zoneID = zone.get("id").toString();
            result[i] = zoneID;

        }


        return result;
    }

    @Override
    public Record[] getRecords() {
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
        String[] returnResult = cf.getZones();
        for (String str : returnResult)
        {
            System.out.println(str);
        }
    }
}
