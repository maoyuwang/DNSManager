import com.squareup.okhttp.*;
import java.util.HashMap;

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

    public static String POST(String url, HashMap<String,String> headersMap, HashMap<String,String> paramsMap){
        Headers headers = Headers.of(headersMap);
        String resultStr = null;
        FormEncodingBuilder formBuilder = new FormEncodingBuilder();

        if(paramsMap!=null)
        {
            for(String key : paramsMap.keySet())
            {
                String value = paramsMap.get(key);
                formBuilder.add(key,value);
            }
        }


        RequestBody form = formBuilder.build();
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
