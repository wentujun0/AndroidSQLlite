package com.example.jk.sqllites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisteActivity extends AppCompatActivity {
   public EditText editText1 ;
    public EditText editText2 ;
    public EditText editText3 ;
    public EditText editText4 ;
    public Button button1;
    public Button button2;

    public TextView displayTextView;
    public DBAdapter dbAdapter;

    //
    public String un;
    public String upd;
    public String na;
    public int age;
    public String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        displayTextView = findViewById(R.id.textView6);



        dbAdapter = new DBAdapter(this);
        //dbAdapter.delete_table();
        dbAdapter.open();//打开数据库


        //监听器进行注册
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获得前台传值
                un = editText1.getText().toString();
                upd = editText2.getText().toString();
                na = editText3.getText().toString();
                temp = editText4.getText().toString();
                age =   Integer.parseInt(temp);//转换为整型的数字


//                un = "12";
//                upd = "12";
//                na = "12";

              //  age = 12 ;//转换为整型的数字
                //
                Users users = new Users();
                users.UserName =un;
                users.UserPassword= upd;
                users.Name = na;
                users.Age = age;

                long rowId = dbAdapter.insert(users);

                if (rowId == -1) {
                    displayTextView.setText("insert database error!");
                } else {
                    displayTextView.setText("insert database correct: "+rowId);
                }

            }
        });

        //dbAdapter.close();//关闭数据库

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText1.setText("");
                editText2.setText("");
                editText3.setText("");
                editText4.setText("");

            }
        });


    }
}
