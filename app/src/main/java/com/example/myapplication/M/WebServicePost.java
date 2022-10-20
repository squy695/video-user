package com.example.myapplication.M;

import static com.example.myapplication.Tool.parseInfo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 使用Post方法获取Http服务器数据
 */

public class WebServicePost {

    public static String executeHttpPost(String[] paras, String address) {
        HttpURLConnection connection = null;
        InputStream in = null;

        try {
            // 对address进行switch
            String Url = "http://我们服务端的URL:8080/Context Path/" + address;
            try {
                // POST报文构造
                URL url = new URL(Url);
                connection = (HttpURLConnection) url.openConnection();

                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setReadTimeout(8000);//传递数据超时

                connection.setUseCaches(false);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                connection.connect();
                System.out.println("已建立HTTP连接");

                // 对服务器的输入流，这里往流里传入了name和password信息
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());

                switch (address) {
                    case "Log":
                        String data = "username=" + URLEncoder.encode(paras[0], "UTF-8") + "&password=" + URLEncoder.encode(paras[1], "UTF-8");
                        // POST写入
                        out.writeBytes(data);
                        out.flush();
                        out.close();
                        System.out.println("安卓端数据已发出");

                        // 得到服务器的状态码
                        int resultCode = connection.getResponseCode();
                        if (HttpURLConnection.HTTP_OK == resultCode) {
                            // TODO 这里的代码是不是需要改成while循环，才能传输List？close是break条件
                            in = connection.getInputStream();
                            // 返回服务器的响应报文
                            return parseInfo(in);
                        }
                        return null;

                    case "Reg":
                        break;
                        // TODO 更多的case
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //意外退出时，连接关闭保护
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}