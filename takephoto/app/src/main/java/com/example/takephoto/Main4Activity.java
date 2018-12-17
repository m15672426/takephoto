package com.example.takephoto;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.takephoto.R;

import static com.example.takephoto.R.id.text_view3;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Intent intent=getIntent();
        String data=intent.getStringExtra("extra_datas");
        TextView textView = (TextView) findViewById(R.id.text_view3);
        textView.setText(data);
    }
}
