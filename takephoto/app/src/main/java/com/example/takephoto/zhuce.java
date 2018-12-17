package com.example.takephoto;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class zhuce extends AppCompatActivity {
    EditText number = null;
    EditText name = null;
    EditText pwd = null;
    Button login;
    String names,numbers,pwn,data;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        init();
    }
    public void init(){
        login=(Button)findViewById(R.id.sssd);
        name=(EditText)findViewById(R.id.sssa);
        number=(EditText)findViewById(R.id.sssb);
        pwd=(EditText)findViewById(R.id.sssc);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                names=name.getText().toString();
                numbers=number.getText().toString();
                pwn=pwd.getText().toString();
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("name",names);
                values.put("nubmer",numbers);
                values.put("pwd",pwn);
                long insert=db.insert("xuesheng",null,values);
                if (insert>0){
                    Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
                }
                db.close();//数据库用完关闭
            }
        });
    }
}
