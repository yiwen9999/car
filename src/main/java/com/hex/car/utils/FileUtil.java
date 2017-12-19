package com.hex.car.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

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

    public static void uploadImgFile(MultipartFile multipartFile, String filePath, String fileName, Long zipFileLimit) throws Exception {
        if (multipartFile.getSize() / zipFileLimit >= 2) { // 大于2m，进行图片压缩
            ImageZipUtil.zipImageFile(multipartFile.getInputStream(), new File(filePath + fileName), 0, 0, 0.25f);
        } else {
            FileUtil.uploadFile(multipartFile.getBytes(), filePath, fileName);
        }
    }

    public static void uploadImgFile64(byte[] file, String filePath, String fileName, Long zipFileLimit) throws Exception {
        InputStream inputStream = new ByteArrayInputStream(file);
        if (inputStream.available() / zipFileLimit >= 2) { // 大于2m，进行图片压缩
            ImageZipUtil.zipImageFile(inputStream, new File(filePath + fileName), 0, 0, 0.25f);
        } else {
            FileUtil.uploadFile(file, filePath, fileName);
        }
    }
}
