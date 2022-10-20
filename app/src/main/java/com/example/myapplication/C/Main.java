package com.example.myapplication.C;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;

public class Main extends AppCompatActivity {

    Button bt1; //搜索
    Button bt2; //上传
    Button bt3; //个人
    Button bt4;
    EditText ed1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        bt1 = findViewById(R.id.Main_1);
        bt2 = findViewById(R.id.Main_2);
        bt3 = findViewById(R.id.Main_3);

        Listener listener = new Listener();
        bt1.setOnClickListener(listener);
        bt2.setOnClickListener(listener);
        bt3.setOnClickListener(listener);

        ed1 = findViewById(R.id.Main_4);
    }

        class Listener implements View.OnClickListener {

            @Override
            public void onClick(View view) {
                String search = ed1.getText().toString();

                switch (view.getId()) {
                    /*搜索*/
                    case R.id.Main_1: {
                        Intent intent=new Intent();
                        intent.setClass(Main.this,Search.class);
                        break;
                    }

                    /*上传*/
                    case R.id.Main_2: {
                        Intent intent=new Intent();
                        intent.setClass(Main.this,Upload.class);
                        break;
                    }

                    /*个人主页*/
                    case R.id.Main_3: {
                        Intent i = new Intent();
                        i.setClass(Main.this, PersonalPage.class);
                        startActivity(i);
                        break;
                    }
                }
            }
        }
}