package com.hdu.gmall;


import com.hdu.gmall.bean.UmsMember;
import com.hdu.gmall.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallUserServiceApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        List<UmsMember> allUser = userService.getAllUser();
        for (UmsMember user: allUser
             ) {
            System.out.println(user.getUsername());
        }
    }

}
