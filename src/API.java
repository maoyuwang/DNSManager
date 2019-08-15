import com.squareup.okhttp.*;
import java.util.HashMap;
import com.alibaba.fastjson.*;

/**
 * A class that implements HTTP GET, POST, PUT, DELETE Methods.
 */
public class API {
    /**
     * Implementing HTTP GET Method.
     * @param url   The url to make request.
     * @param headersMap    A Map contains the headers to make the request.
     * @return  The returned string after execute the GET Request.
     */
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

    /**
     * Implementing HTTP POST Method.
     * @param url   The URL to make post requests
     * @param headersMap    The headers of the post request
     * @param jsonObject    The parameters to this request
     * @return  The result String after executing the POST request.
     */
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


    public static String PUT(String url, HashMap<String,String> headersMap, JSONObject jsonObject){
        Headers headers = Headers.of(headersMap);
        String resultStr = null;

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody form = RequestBody.create(JSON, jsonObject.toString());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .headers(headers)
                .put(form)
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

    public static String DELETE(String url, HashMap<String,String> headersMap, JSONObject jsonObject){
        Headers headers = Headers.of(headersMap);
        String resultStr = null;

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody form = RequestBody.create(JSON, jsonObject.toString());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .headers(headers)
                .delete(form)
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
