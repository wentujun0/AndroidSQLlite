package com.example.jk.sqllites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LoginedActivity extends AppCompatActivity {
Button bu1;
TextView tv2;
TextView tv3;
ListView lv1;

DBAdapter dbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logined);
        bu1 = (Button) findViewById(R.id.bu1);
        tv2 = (TextView)findViewById(R.id.tv2);
        tv3 = (TextView)findViewById(R.id.tv3);
        //lv1 = (ListView) findViewById(R.id.lv1);

        dbAdapter = new DBAdapter(this);
        dbAdapter.open();//打开数据库
        //获取传递的值
        Intent intent = this.getIntent();
        String uname = intent.getStringExtra("username");

        tv2.setText("欢迎用户:"+uname);

        bu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Users[] users = dbAdapter.selectAllData();
                if(users == null)
                {
                    tv3.setText("error!");
                }else{
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < users.length; i++) {
                        stringBuilder.append(users[i].Id);
                        stringBuilder.append(",|  ");
                        stringBuilder.append(users[i].UserName);
                        stringBuilder.append(",|  ");
                        stringBuilder.append(users[i].Name);
                        stringBuilder.append(",|  ");
                        stringBuilder.append(users[i].Age);
                        stringBuilder.append(";\n");
                    }
                    tv3.setText(stringBuilder.toString());
//                    List<String> list = new ArrayList<String>();
//                    for (int i=0 ;i <users.length;i++){
//                        list.add(users[i].Id+" "+users[i].UserName+" "+users[i].Name+" "+users[i].Age);
//                    }
//                   ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(
//                      this,android.R.layout.a,list);
//                    lv1.setAdapter(adapter2);



                }
                //dbAdapter.close();//打开数据库
            }
        });

    }
}
