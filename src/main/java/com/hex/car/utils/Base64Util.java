package com.hex.car.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * User: hexuan
 * Date: 2017/11/21
 * Time: 下午12:09
 */
public class Base64Util {

    // 图片转化成base64字符串
    public static String GetImageStr(FileInputStream in) {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        //读取图片字节数组
        try {
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

    // base64字符串转化成图片
    public static byte[] GenerateImage(String imgStr) {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            return b;
        } catch (Exception e) {
            return null;
        }
    }
}
