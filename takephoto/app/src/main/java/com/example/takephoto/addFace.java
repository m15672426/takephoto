package com.example.takephoto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.takephoto.Face.APIConstants;
import com.example.takephoto.Util.Base64Util;
import com.example.takephoto.Util.FileUtil;
import com.example.takephoto.Util.GsonUtils;
import com.example.takephoto.Util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

public class addFace extends AppCompatActivity {

    EditText number = null;
    EditText pwd = null;
    String name,numbers,data;
    String S="SUCCESS";
    Button login;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_face);
        init();
    }
    private void init(){
        final Button login=(Button)findViewById(R.id.btn);
        number=(EditText)findViewById(R.id.number);
        pwd=(EditText)findViewById(R.id.pwd);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(addFace.this, "人脸上传中",
                        "Loading. Please wait...", true);
                // 设置ProgressDialog 标题图标
                dialog.setIcon(R.drawable.shape_progressbar_bg);
                // 设置ProgressDialog 按退回按键可以取消
                dialog.setCancelable(true);
                // 让ProgressDialog显示
                dialog.show();
            name=pwd.getText().toString();
            numbers=number.getText().toString();
            if(numbers.equals("18805198520"))
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=getIntent();
                        String Filepath=intent.getStringExtra("extra");
                        if(Filepath!=null) {
                            //Toast.makeText(addFace.this,"正在进行人脸检测与上传请稍后......",Toast.LENGTH_LONG).show();
                            System.out.println(Filepath);
                            data = add(Filepath, name);
                            System.out.println(data);
                            System.out.println(data);
                            if (data.indexOf(S) > 0) {
                                intent = new Intent(addFace.this, Main4Activity.class);
                                intent.putExtra("extra_datas", data);
                                startActivity(intent);
                            } else {
                                intent = new Intent(addFace.this, Main5Activity.class);
                                intent.putExtra("extra_datas", data);
                                startActivity(intent);
                            }
                        }
                        else {
                            intent = new Intent(addFace.this, useDirections.class);
                            startActivity(intent);
                        }
                    }
                }).start();
            }
            else {
                Toast.makeText(addFace.this,"账号不存在",Toast.LENGTH_LONG).show();
            }
            }
        });
    }

    public static String add(String Filepath,String name) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        try {
            String image = Base64Util.encode(FileUtil.readFileByBytes(Filepath));
            Map<String, Object> map = new HashMap<>();
            map.put("image", image);
            map.put("group_id", "1");
            map.put("user_id", name);
            map.put("user_info", "");
            map.put("liveness_control", "NORMAL");
            map.put("image_type", "BASE64");
            map.put("quality_control", "HIGH");
            String param = GsonUtils.toJson(map);
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = APIConstants.TOKEN;
            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
