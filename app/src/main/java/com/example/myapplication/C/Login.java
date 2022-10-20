package com.example.myapplication.C;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.M.SQLite;
import com.example.myapplication.Tool;

import java.util.List;

public class Login extends AppCompatActivity {

    Button bt1;
    Button bt2;
    Button bt3;
    EditText ed1;
    EditText ed2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        /*设备记住该用户*/
        if(SQLite.local()){
            Intent i = new Intent();
            i.setClass(Login.this, Main.class);
            startActivity(i);
        }

        bt1 = findViewById(R.id.login_1);
        bt2 = findViewById(R.id.login_2);
        bt3 = findViewById(R.id.login_3);
        Listener listener = new Listener();
        bt1.setOnClickListener(listener);
        bt2.setOnClickListener(listener);
        bt3.setOnClickListener(listener);

        ed1 = findViewById(R.id.login_4);
        ed2 = findViewById(R.id.login_5);
    }

    class Listener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.login_1: { /*注册*/
                    Intent i = new Intent();
                    i.setClass(Login.this, Register.class);
                    startActivity(i);
                    break;
                }

                case R.id.login_2: { /*登录*/
                    int temp = Tool.userIsValid(ed1.getText().toString(),
                            ed2.getText().toString());
                    if (temp >= 0) {  //用户存在
                        if (temp == 1) {
                            Tool.isVIP = true;
                        } else if (temp == 2) {
                            Tool.isManager = true;
                        } else {
                            Tool.isUser = true;
                        }

                        /*设备记住当前用户*/
                        List<UserBean> list=UserConnector.select("select * from user where email=\""+ed1.getText().toString()+"\"");
                        SQLite.remember(list);

                        Intent i = new Intent();
                        i.setClass(Login.this, Main.class);
                        startActivity(i);
                    } else if (temp == -1) {  // 密码错误
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Login.
                                this);
                        dialog.setTitle("登陆失败");
                        dialog.setMessage("密码错误");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("忘记密码?", new DialogInterface.
                                OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent();
                                i.setClass(Login.this, Register.class);
                                startActivity(i);
                            }

                        });
                        dialog.setNegativeButton("好吧", new DialogInterface.
                                OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO 关闭弹窗？
                            }
                        });
                        dialog.show();
                    } else { //用户不存在
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Login.
                                this);
                        dialog.setTitle("登陆失败");
                        dialog.setMessage("账户不存在，快去注册吧");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("去注册", new DialogInterface.
                                OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent();
                                i.setClass(Login.this, Register.class);
                                startActivity(i);
                            }

                        });
                        dialog.setNegativeButton("算了吧", new DialogInterface.
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

                case R.id.login_3: { /*退出*/
                    finish();
                    break;
                }
            }
        }
    }

}