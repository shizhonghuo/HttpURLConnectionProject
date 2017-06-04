package com.example.administrator.myapplication;

import android.provider.Settings;
import android.text.TextUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/6/4.
 */

public class ConnManager {

    /**
     * 配置HttpURLConnection 的默认参数
     */
    public static HttpURLConnection getHttpURLConnection(String url){
        HttpURLConnection conn=null;
        try{
            URL mUrl=new URL(url);
            conn=(HttpURLConnection) mUrl.openConnection();
            // 设置请求命令为get
            conn.setRequestMethod("GET");
            // 设置连接超时时间
            conn.setConnectTimeout(15000);
            //设置读取超时时间
            conn.setReadTimeout(15000);
            //设置头信息
            //设置传送内容是可序列化的Java对象
            conn.setRequestProperty("Content-type", "application/x-java-serialized-object");
            conn.setRequestProperty("Connection","Keep-Alive");
            //接收输入流
            conn.setDoInput(true);
            //传递参数时需要开启
            conn.setDoOutput(true);

        }catch(IOException e){
            e.printStackTrace();
        }
        return conn;
    }

    /*
    * 组织请求参数，并将参数写入到输出流
    */
     public static void getParam(OutputStream output, Map<String,String> map){
         StringBuffer mStringBuilder =new StringBuffer();
         Iterator<Map.Entry<String,String>> it=map.entrySet().iterator();
         try {
             while(it.hasNext()){
                 if(!TextUtils.isEmpty(mStringBuilder)){
                     mStringBuilder.append("&");
                 }
                 Map.Entry<String,String> entry=it.next();
                 mStringBuilder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                 mStringBuilder.append("=");
                 mStringBuilder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
             }
             BufferedWriter write=new BufferedWriter(new OutputStreamWriter(output,"UTF-8"));
             write.write(mStringBuilder.toString());
             write.flush();
             write.close();
         }catch (UnsupportedEncodingException e){
             e.printStackTrace();
         }catch (IOException e){
             e.printStackTrace();
         }
     }

}
