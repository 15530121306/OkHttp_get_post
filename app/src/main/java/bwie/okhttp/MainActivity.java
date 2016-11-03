package bwie.okhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiview();

        /**
         * 支持同步异步
         */
//        getExecute();//get方式两个参数：url callback
//        getEnqueue();//get一个参数： url
//          postEnqueue();//post 传键值对方式
        postJson();//post  传json串形式
    }

    private void postJson() {
        String json="{\"page\":\"1\",{\"rows\":\"20\"}";
        OkHttpUtils.postEnqueue("http://www.tngou.net/api/cook/list", json, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result=response.body().string();
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(result);
                    }
                });
            }
        });
    }

    private void postEnqueue() {
        Map<String,String> map=new HashMap<>();
        map.put("page","1");
        map.put("rows","20");
        OkHttpUtils.postEnqueue("http://www.tngou.net/api/cook/list",map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result=response.body().string();
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(result);
                    }
                });
            }
        });
    }

    private void getEnqueue() {
        OkHttpUtils.get("http://www.baidu.com", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                tv.setText(response.body().string());
                final String result=response.body().string();
                /**
                 * 在子线程中给tv赋值
                 */
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(result);
                    }
                });
            }
        });
    }

    private void getExecute() {
        new Thread(){
            @Override
            public void run() {
                final String result=OkHttpUtils.get("http://www.baidu.com");
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(result);
                    }
                });
            }
        }.start();
    }

    private void intiview() {
        tv = (TextView)findViewById(R.id.tv);

    }
}
