package com.hex.car.utils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 下午3:05
 */
public class FileUtil {
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
