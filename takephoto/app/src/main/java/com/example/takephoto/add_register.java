package com.example.takephoto;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class add_register extends AppCompatActivity {
    EditText number = null;
    EditText pwd = null;
    Button login,ref;
    String name,numbers,data;
    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_register);
        init();
    }
    private void init()
    {
        login=(Button)findViewById(R.id.sssc);
        ref=(Button)findViewById(R.id.sssd);
        number=(EditText)findViewById(R.id.sssa);
        pwd=(EditText)findViewById(R.id.sssb);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=pwd.getText().toString();
                numbers=number.getText().toString();
                Intent intent = new Intent(add_register.this, MainActivity.class);
                intent.putExtra("extra2",name);
                intent.putExtra("extra1",numbers);
                startActivity(intent);
            }
        });
        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add_register.this, zhuce.class);
                startActivity(intent);
            }
        });
    }

}
