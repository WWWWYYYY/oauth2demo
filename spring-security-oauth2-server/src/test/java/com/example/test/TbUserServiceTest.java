package com.example.test;

import com.example.OAuth2ServerApplication;
import com.example.domain.TbUser;
import com.example.service.TbUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OAuth2ServerApplication.class)
@Transactional
@Rollback(value = false)
public class TbUserServiceTest {

    @Autowired
    private TbUserService tbUserService;
    @Test
    public void testSelectByUsername(){
        TbUser admin = tbUserService.selectOneTbUserByUsername("admin");
        System.out.println(admin);
    }
}  
