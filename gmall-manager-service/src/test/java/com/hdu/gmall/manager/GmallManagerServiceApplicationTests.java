package com.hdu.gmall.manager;

import com.hdu.gmall.bean.PmsBaseCatalog1;
import com.hdu.gmall.service.CatalogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallManagerServiceApplicationTests {

    @Autowired
    private CatalogService catalogService;
    @Test
    public void contextLoads() {
        List<PmsBaseCatalog1> catalog1 = catalogService.getCatalog1();
        for (PmsBaseCatalog1 cata:
             catalog1) {
            System.out.println(cata.getName());
        }
    }

}
