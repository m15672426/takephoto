package com.example.takephoto.Face;

import com.example.takephoto.Util.Base64Util;
import com.example.takephoto.Util.FileUtil;
import com.example.takephoto.Util.GsonUtils;
import com.example.takephoto.Util.HttpUtil;

import java.util.HashMap;
import java.util.Map;


public class FaceDetect {
    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String detect() {
        // 请求url
        String Filepath="D:/picture/zhou.jpg";
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        try {
            String image = Base64Util.encode(FileUtil.readFileByBytes(Filepath));
            Map<String, Object> map = new HashMap<>();
            map.put("image", image );
            map.put("face_field", "faceshape,facetype,age,beauty,gender");
            map.put("image_type", "BASE64");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.d198402c8365a0f7ff6d46f47e7cdbd0.2592000.1541947106.282335-14383239";

            String result = HttpUtil.post(url,accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        FaceDetect.detect();
    }
}
