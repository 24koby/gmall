package com.hdu.gmall.manager;

import com.hdu.gmall.bean.PmsBaseCatalog1;
import com.hdu.gmall.service.CatalogService;
import com.hdu.gmall.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallManagerServiceApplicationTests {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private CatalogService catalogService;
    @Test
    public void contextLoads() {
        redisUtil.getJedis();
    }

}
