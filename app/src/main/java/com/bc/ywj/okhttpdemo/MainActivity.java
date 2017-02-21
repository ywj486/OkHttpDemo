package com.bc.ywj.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
    }

    public void doGet(View view) throws IOException {
        //1.拿到OkHttpClient对象  全局执行者
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url("https://www.baidu.com/").build();
        //3.将Request封装为call，相当于runnable
        Call call = okHttpClient.newCall(request);
        //4.执行call
        // call.execute();直接执行
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "onFailure：" + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                Log.e("TAG", "onResponse:" + res);
                //异步  耗时操作需要在UI主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(res);
                    }
                });
            }
        });
    }
    public void doPost(View view){

    }
}
