import com.alibaba.fastjson.*;
import com.squareup.okhttp.*;
import java.util.*;
import java.io.*;
import okio.*;

public class CloudFlare extends DNSProvider {
    public CloudFlare(String pubKey, String privKey) {
        super(pubKey, privKey);
    }



    @Override
    public Record[] getRecords() {

        String url = "https://api.cloudflare.com/client/v4/zones?page=1&per_page=10&order=type&direction=asc";
        OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .header("User-Agent", "OkHttp Headers.java")
                    .addHeader("X-Auth-Email",getPublicKey())
                    .addHeader("X-Auth-Key",getPrivateKey())
                    .addHeader("Content-Type","application/json")
                    .url(url)
                    .build();
            try{
                Response r = client.newCall(request).execute();
                String resultStr = r.body().string();
                JSONObject j = JSON.parseObject(resultStr);
                JSONArray ja = j.getJSONArray("result");
                JSONObject ja2 = ja.getJSONObject(0);
                System.out.println(ja2.get("name"));


            }
            catch (Exception e)
            {
                e.printStackTrace();
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
