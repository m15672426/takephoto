package com.example.takephoto;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.takephoto.Face.APIConstants;
import com.example.takephoto.Util.Base64Util;
import com.example.takephoto.Util.FileUtil;
import com.example.takephoto.Util.GsonUtils;
import com.example.takephoto.Util.HttpUtil;

import java.lang.String;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.lang.Integer.valueOf;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private static final int REQ_THUMB = 222;
    private static final int REQ_GALLERY = 333;
    private static final String TAG = "MainActivity";
    private static final int REQ_TAKE_PHOTO = 444;
    Button mThumbnail, mFullSize, mAddGallery,match,login;
    ImageView mImageView;
    String S="SUCCESS";
    String A="score";
    String picture;
    TextView a;
    TextView zhanghao;
    TextView mima;
    String get1;
    String get2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());*/
        Runnable networkTask = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.add_item:
                Intent intent=new Intent(MainActivity.this,add_register.class);
                startActivity(intent);
                break;
            case R.id.remove_item:
                get1=null;
                get2=null;
                Intent intent2=new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.f5:
                Intent intent1=new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.remove:
                finish();
                break;
            default:
        }
        return true;
    }

    public static String search(String Filepath) throws IOException {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        String image = Base64Util.encode(FileUtil.readFileByBytes(Filepath));
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image",image);
            map.put("liveness_control", "NORMAL");
            map.put("group_id_list", "1");
            map.put("image_type", "BASE64");
            map.put("quality_control", "NORMAL");
            String param = GsonUtils.toJson(map);
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = APIConstants.TOKEN;
            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @SuppressLint("CutPasteId")
    private void init() {
        zhanghao=(TextView)findViewById(R.id.zhanghao);
        mima=(TextView)findViewById(R.id.mima);
        Intent intent=getIntent();
        get1=intent.getStringExtra("extra1");
        get2=intent.getStringExtra("extra2");
        if(get1!=null&&get2!=null)
        {
        zhanghao.setText(get1);
        mima.setText(get2);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }
        mThumbnail = (Button) findViewById(R.id.thumbnail);
        //mFullSize = (Button) findViewById(R.id.fullSize);
        mAddGallery = (Button) findViewById(R.id.addGallery);
        mImageView = (ImageView) findViewById(R.id.imageView);
        match = (Button) findViewById(R.id.match);
        login = (Button) findViewById(R.id.login);
        mThumbnail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainActivity.this,useDirections.class);
                startActivity(intent1);
                /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {//判断是否有相机应用
                    startActivityForResult(takePictureIntent, REQ_THUMB);
                }*/
            }
        });
        /*mFullSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {//判断是否有相机应用
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createPublicImageFile();//创建临时图片文件
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        //FileProvider 是一个特殊的 ContentProvider 的子类，
                        //它使用 content:// Uri 代替了 file:/// Uri. ，更便利而且安全的为另一个app分享文件
                        Uri photoURI = FileProvider.getUriForFile(MainActivity.this,
                                "com.youga.fileprovider",
                                photoFile);
                        Log.i(TAG, "photoURI:" + photoURI.toString());
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQ_GALLERY);
                    }
                    picture = photoFile.getAbsolutePath();
                }
                galleryAddPic();
            }

        });*/
        mAddGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {//判断是否有相机应用
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createPublicImageFile();//创建临时图片文件
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(MainActivity.this,
                                "com.youga.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQ_GALLERY);
                    }
                    picture=photoFile.getAbsolutePath();
                }
                galleryAddPic();
            }

        });

        match.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(mPublicPhotoPath!=null) {
                                String data = search(mPublicPhotoPath);
                                int i = data.indexOf(A);
                                if (data.indexOf(S) > 0) {
                                    String abc = data.substring(i + 7, i + 10);
                                    Log.i(TAG, "score:" + abc);
                                    float a = Float.valueOf(abc);
                                    if (a != 100) {
                                        abc = data.substring(i + 7, i + 9);
                                        a = Float.valueOf(abc);
                                    }
                                    if (a > 89) {
                                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                                        intent.putExtra("extra_data", data);
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                                        intent.putExtra("extra_data", data);
                                        startActivity(intent);
                                    }
                                } else {
                                    Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                                    intent.putExtra("extra_data", data);
                                    startActivity(intent);
                                }
                            }
                            else{
                                Intent intent = new Intent(MainActivity.this, useDirections.class);
                                startActivity(intent);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                /*try {
                    if(mPublicPhotoPath!=null) {
                        Toast.makeText(MainActivity.this,"正在进行人脸比对请稍后......",Toast.LENGTH_LONG).show();
                        String data = search(mPublicPhotoPath);
                        int i = data.indexOf(A);
                        if (data.indexOf(S) > 0) {
                            String abc = data.substring(i + 7, i + 10);
                            Log.i(TAG, "score:" + abc);
                            float a = Float.valueOf(abc);
                            if (a != 100) {
                                abc = data.substring(i + 7, i + 9);
                                a = Float.valueOf(abc);
                            }
                            if (a > 89) {
                                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                                intent.putExtra("extra_data", data);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                                intent.putExtra("extra_data", data);
                                startActivity(intent);
                            }
                        } else {
                            Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                            intent.putExtra("extra_data", data);
                            startActivity(intent);
                        }
                    }
                    else{
                        Intent intent = new Intent(MainActivity.this, useDirections.class);
                        startActivity(intent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        });

        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,addFace.class);
                intent.putExtra("extra",mPublicPhotoPath);
                startActivity(intent);
            }
        });
    }
    String mCurrentPhotoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //创建临时文件,文件前缀不能少于三个字符,后缀如果为空默认未".tmp"
        File image = File.createTempFile(
                imageFileName,  /* 前缀 */
                ".jpg",         /* 后缀 */
                storageDir      /* 文件夹 */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    String mPublicPhotoPath;

    private File createPublicImageFile() throws IOException {
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);
        // Create an image file name
        Log.i(TAG, "path:" + path.getAbsolutePath());
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File image = File.createTempFile(
                imageFileName,  /* 前缀 */
                ".jpg",         /* 后缀 */
                path      /* 文件夹 */
        );
        mPublicPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mPublicPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_THUMB://返回结果
                if (resultCode != Activity.RESULT_OK) return;
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                mImageView.setImageBitmap(imageBitmap);
                break;
            case REQ_TAKE_PHOTO://返回结果
                if (resultCode != Activity.RESULT_OK) return;


                // Get the dimensions of the View
                int targetW = mImageView.getWidth();
                int targetH = mImageView.getHeight();

                // Get the dimensions of the bitmap

                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
                int photoW = bmOptions.outWidth;
                int photoH = bmOptions.outHeight;

                // Determine how much to scale down the image
                int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                bmOptions.inPurgeable = true;

                Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
                mImageView.setImageBitmap(bitmap);
                break;

            case REQ_GALLERY:
                if (resultCode != Activity.RESULT_OK) return;


                // Get the dimensions of the View
                targetW = mImageView.getWidth();
                targetH = mImageView.getHeight();

                // Get the dimensions of the bitmap
                bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(mPublicPhotoPath, bmOptions);
                photoW = bmOptions.outWidth;
                photoH = bmOptions.outHeight;

                // Determine how much to scale down the image
                scaleFactor = Math.min(photoW / targetW, photoH / targetH);

                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                bmOptions.inPurgeable = true;

                bitmap = BitmapFactory.decodeFile(mPublicPhotoPath, bmOptions);
                mImageView.setImageBitmap(bitmap);
                break;
        }
    }
}
