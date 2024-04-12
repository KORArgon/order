package com.argon.order.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    /**
     * 첨부파일 업로드 기능
     * @param is`
     * @param path
     * @param fileName
     * @throws IOException
     */
    public static void getFileUploadAtchmnfl(InputStream is, String path, String fileName) throws IOException {
        File folder = new File(path);
        if (!folder.exists()) folder.mkdir();
        FileOutputStream fos = new FileOutputStream(path+fileName);
        int readCount = 0;
        byte[] buffer = new byte[1024];

        while ((readCount = is.read(buffer)) != -1) {
            fos.write(buffer, 0, readCount);
        }
    }

}
