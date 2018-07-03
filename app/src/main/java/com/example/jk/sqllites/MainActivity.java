package com.example.jk.sqllites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
public Button button1;//登录
public Button button2;//注册
public EditText editText1;//用户名
public EditText editText2;//密码
public TextView displayTextView;
public DBAdapter dbAdapter;

public String un;
public String upd;
//定义请求码
    final  int REQUEST1 = 1;

    static final String TAG = "MainAcitivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        displayTextView = findViewById(R.id.textView4);

        dbAdapter = new DBAdapter(this);
        dbAdapter.open();//打开数据库


        //登录验证
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //传递,关联
                Intent intent = new Intent(MainActivity.this,LoginedActivity.class);
//获得前台的传值
                un = editText1.getText().toString();
                upd = editText2.getText().toString();

                Users[] users = dbAdapter.selectOneData(un);
                Log.i(TAG, "size of users："+users.length);

                 String up = users[0].UserPassword;
                 System.out.println("游标:"+users);


                if (null == users) {
                    displayTextView.setText("database is empty!");

                }
                else{
                    if(up.equals(upd)){
                        displayTextView.setText("success!");
                        //封装以及传递
                        intent.putExtra("username",un);
                        startActivityForResult(intent,REQUEST1);

                    }
                    else{
                        displayTextView.setText("error!");
                    }
                }
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisteActivity.class);
                startActivity(intent);
            }
        });


    }


}
