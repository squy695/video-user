package com.example.myapplication;
// 如果你需要写一些辅助的函数，或者全局变量，那你可以写在这里
import android.content.Context;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class Tool {
    public static boolean nowName;
    public static boolean isUser = false;
    public static boolean isVIP = false;
    public static boolean isManager = false;

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static String getVertifaction() {
        String temp = "";
        Random random = new Random();
        for (int i = 0; i < 6; ++i) {
            temp += Integer.toString(Math.abs(random.nextInt()) % 10);
        }
        return temp;
    }

    //得到服务器端来的字节输入流，将字节输入流转化为String类型并返回
    public static String parseInfo(InputStream inputStream){
        BufferedReader reader = null;
        String line = "";
        StringBuilder response = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while((line = reader.readLine()) != null){
                response.append(line);
            }
            return response.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}