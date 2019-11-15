package com.hdu.gmall.manager;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallManagerWebApplicationTests {

    @Test
    public void contextLoads() throws IOException, MyException {
        String path = GmallManagerWebApplicationTests.class.getResource("/tracker.conf").getPath();
        ClientGlobal.init(path);
        TrackerClient client = new TrackerClient();
        TrackerServer trackerServer = client.getConnection();
        StorageClient storageClient = new StorageClient(trackerServer,null);
        String[] uploadFile = storageClient.upload_file("C:/timg.jpg", "jpg", null);
        for (String uploadInfo: uploadFile) {
            System.out.println(uploadInfo);
        }
    }



}
