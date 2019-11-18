package com.hdu.gmall.manager.util;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class PmsUploadFile {
    private static final String DEFAULT_URL = "http://192.168.25.112";
    private static final String DEFAULT_CONF = "/tracker.conf";
    public static String uploadFile(MultipartFile multipartFile) {
        StringBuilder imgUrl = new StringBuilder();
        imgUrl.append(DEFAULT_URL);
        String path = PmsUploadFile.class.getResource(DEFAULT_CONF).getPath();
        try {
            ClientGlobal.init(path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        TrackerClient client = new TrackerClient();
        TrackerServer trackerServer = null;
        try {
            trackerServer = client.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StorageClient storageClient = new StorageClient(trackerServer,null);
        byte[] bytes = null;
        try {
            bytes = multipartFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = multipartFile.getOriginalFilename();
        int pos = filename.lastIndexOf(".");
        String extFileName = filename.substring(pos + 1);
        try {
            String[] urls = storageClient.upload_appender_file(bytes, extFileName, null);
            for (String url:urls) {
                imgUrl.append("/"+url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return imgUrl.toString();
    }
}
