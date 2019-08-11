import com.squareup.okhttp.*;
import java.util.HashMap;
import com.alibaba.fastjson.*;

public class API {
    public static String GET(String url, HashMap<String,String> headersMap){
        Headers headers = Headers.of(headersMap);
        String resultStr = null;



        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .headers(headers)
                .url(url)
                .build();
        try{
            Response r = client.newCall(request).execute();
            resultStr = r.body().string();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return resultStr;

    }

    public static String POST(String url, HashMap<String,String> headersMap, JSONObject jsonObject){
        Headers headers = Headers.of(headersMap);
        String resultStr = null;

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody form = RequestBody.create(JSON, jsonObject.toString());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .headers(headers)
                .post(form)
                .url(url)
                .build();
        try{
            Response r = client.newCall(request).execute();
            resultStr = r.body().string();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return resultStr;

    }
}
