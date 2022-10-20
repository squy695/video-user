package com.example.myapplication.C;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.M.WebServicePost;
import com.example.myapplication.R;
import com.example.myapplication.Tool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLOutput;

public class Register extends AppCompatActivity {

    Button bt1; // 验证码
    Button bt2; // 注册
    Button bt3; // 离开
    EditText ed1; // 邮箱
    EditText ed2; // 验证码
    EditText ed3; // 密码
    EditText ed4; // 确认密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        bt1 = findViewById(R.id.register_1);
        bt2 = findViewById(R.id.register_2);
        bt3 = findViewById(R.id.register_3);
        Listener listener = new Listener();
        bt1.setOnClickListener(listener);
        bt2.setOnClickListener(listener);
        bt3.setOnClickListener(listener);

        ed1 = findViewById(R.id.register_4);
        ed2 = findViewById(R.id.register_5);
        ed3 = findViewById(R.id.register_6);
        ed4 = findViewById(R.id.register_7);

    }

    class Listener implements View.OnClickListener {
        String vertifaction;

        @Override
        public void onClick(View view) {
            String email = ed1.getText().toString();
            String vert = ed2.getText().toString();
            String pwd = ed3.getText().toString();
            String pwdCheck = ed4.getText().toString();


            switch (view.getId()) {
                // 发验证码
                case R.id.register_1: {
                    try {
                        vertifaction = Tool.getVertifaction();
                        VertThread mythread = new VertThread(email, vertifaction);
                        Thread thread = new Thread(mythread);
                        thread.start();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }

                /*注册*/
                case R.id.register_2: {
                    if (vertifaction.equals(vert)) {
                        if (pwd.equals(pwdCheck)) {

                            RegThread regThread=new RegThread(email,pwd);
                            Thread thread=new Thread(regThread);
                            thread.start();

                            AlertDialog.Builder dialog = new AlertDialog.Builder(Register.
                                    this);
                            dialog.setTitle("ok...");
                            dialog.setMessage("注册成功");
                            dialog.setCancelable(false);
                            dialog.setNegativeButton("好的,返回登录界面", new DialogInterface.
                                    OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i=new Intent();
                                    i.setClass(Register.this,Login.class);
                                    startActivity(i);
                                }
                            });
                            dialog.show();


                        } else {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(Register.
                                    this);
                            dialog.setTitle("oops...");
                            dialog.setMessage("二次密码错误");
                            dialog.setCancelable(false);
                            dialog.setNegativeButton("好吧", new DialogInterface.
                                    OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //TODO 关闭弹窗？
                                }
                            });
                            dialog.show();
                        }
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Register.
                                this);
                        dialog.setTitle("oops...");
                        dialog.setMessage("验证码错误");
                        dialog.setCancelable(false);
                        dialog.setNegativeButton("好吧", new DialogInterface.
                                OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO 关闭弹窗？
                            }
                        });
                        dialog.show();
                    }
                    break;
                }

                case R.id.register_3: { /*离开*/
                    Intent i = new Intent();
                    i.setClass(Register.this, Login.class);
                    startActivity(i);
                    break;
                }
            }
        }
    }

    // 点击“获取验证码”，启动该线程
    class VertThread implements Runnable {
        String email;
        String vert;

        public VertThread(String email, String vert) {
            this.email = email;
            this.vert = vert;
        }

        @Override
        public void run() {
            // 我们要实现的是HTTP连接
            try {
                // 把发送验证码需要的邮箱和验证码交给服务器代发
                String s = WebServicePost.executeHttpPost(new String[]{email, vert}, "RegV");
                System.out.println("响应报文：" + s);
                System.out.println("验证码发送成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 点击“注册”按钮触发该线程 提交email，pwd
    class RegThread implements Runnable {
        String email;
        String pwd;

        public RegThread(String email, String pwd) {
            this.email = email;
            this.pwd = pwd;
        }

        @Override
        public void run() {
            // 我们要实现的是HTTP连接
            try {
                String s = WebServicePost.executeHttpPost(new String[]{email, pwd}, "Reg");
                System.out.println("响应报文：" + s);
                if(s.equals("true")){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Register.
                            this);
                    dialog.setTitle("ok...");
                    dialog.setMessage("注册成功");
                    dialog.setCancelable(false);
                    dialog.setNegativeButton("好", new DialogInterface.
                            OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //TODO 关闭弹窗？
                        }
                    });
                    dialog.show();
                }else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Register.
                            this);
                    dialog.setTitle("oops...");
                    dialog.setMessage("注册失败，可能是邮箱占用");
                    dialog.setCancelable(false);
                    dialog.setNegativeButton("好吧", new DialogInterface.
                            OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //TODO 关闭弹窗？
                        }
                    });
                    dialog.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
