package com.example.takephoto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class useDirections extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_directions);
        main();
    }
    private void main(){
        TextView use=(TextView)findViewById(R.id.use);
        TextView use1=(TextView)findViewById(R.id.use1);
        TextView use2=(TextView)findViewById(R.id.use2);
        use.setText("1.使用人脸图像拍摄录入按钮，拍摄人脸图片，图片将显示在主界面下侧方便您预览。");
        use1.setText("2.如果您尚未进行过人脸注册,且进行过1操作后，可以点击注册按钮，填写您的账号，成功即可录入。");
        use2.setText("3.如果您已注册过人脸信息，且进行过1操作后，点击登录即可。");
        Button ret=(Button)findViewById(R.id.ret);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(useDirections.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
