package bwie.okhttp;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Shixj on 2016/11/3.
 */
public class OkHttpUtils {

    private static OkHttpClient client;
    public static final MediaType JSON= MediaType.parse("application/json;charset=utf-8");

    private OkHttpUtils(){}
    public static OkHttpClient getOkhttpClient(){
        if(client==null){
//            client=new OkHttpClient();
            HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client=new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .build();
        }
        return client;
    }

    public static void postEnqueue(String url,String json,Callback callback){
        RequestBody requestBody=RequestBody.create(JSON,json);
        Request request=new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        getOkhttpClient().newCall(request).enqueue(callback);
    }

    /**
     * post方式的两种传值
     *
     * 1.直接传json
     * 2.传键值对
     */
    public static void postEnqueue(String url, Map<String,String> map, Callback callback){
        FormBody.Builder formBody=new FormBody.Builder();
        for(String key:map.keySet()){
            formBody.add(key,map.get(key));
        }
        RequestBody requestBody=formBody.build();

        Request request=new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        getOkhttpClient().newCall(request).enqueue(callback);
    }


    public static void get(String url, Callback callback){
        Request request=new Request.Builder().url(url).build();
        getOkhttpClient().newCall(request).enqueue(callback);
    }

    /**
     * 异步get
     * @param url
     * @return
     */
    public static String get(String url){
        /**
         * Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应
         */
//        OkHttpClient client=new OkHttpClient();   提成全局变量公用一个
        Request request =new Request.Builder()
                .url(url).build();
        try {
            Call call=getOkhttpClient().newCall(request);
            Response response=call
                    .execute();
            if(response.isSuccessful()){

                /**
                 * Response.body()返回ResponseBody类
                 */
                return response.body().string();
            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
