package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private String converStreamToString(InputStream in) throws IOException{
        String respond=null;
        BufferedReader reader=new BufferedReader(new InputStreamReader(in));
        StringBuffer sb=new StringBuffer();
        String line;
        while((line=reader.readLine())!=null){
            sb.append(line+"\n");
        }
        respond=sb.toString();
        return respond;
    }

    private void useHttpUrlConnectionThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                usrHttpURLConnectionGet("www.baidu.com");
            }
        });
    }

    private void usrHttpURLConnectionGet(String url){

        HttpURLConnection conn=ConnManager.getHttpURLConnection(url);
        try{
            Map<String,String> map =new HashMap<>();
            map.put("username","Li");
            map.put("passard","123");
            ConnManager.getParam(conn.getOutputStream(),map);
            conn.connect();
            InputStream mInputStream=conn.getInputStream();
            int code =conn.getResponseCode();
            String respond=converStreamToString(mInputStream);
            Log.i("wangshu", "请求状态码:" + code + "\n请求结果:\n" + respond);
            mInputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
