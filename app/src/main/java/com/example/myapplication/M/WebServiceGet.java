package com.example.myapplication.M;

import static com.example.myapplication.Tool.parseInfo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 使用get方法获取Http服务器数据
 */

public class WebServiceGet {

    public static String executeHttpGet(String username,String password,String address){
        HttpURLConnection connection = null;
        InputStream in = null;

        try{
            // HelloWeb是Context Path；address是Servlet Path
            String Url = "http://我们服务端的URL:8080/Context Path/" + address;
            String path = Url + "?username=" + username + "&password=" + password;
            try {
                URL url = new URL(path);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);//建立连接超时
                connection.setReadTimeout(8000);//传递数据超时

                in = connection.getInputStream();
                return parseInfo(in);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //意外退出时，连接关闭保护
            if(connection != null){
                connection.disconnect();
            }
            if(in != null){
                try{
                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}