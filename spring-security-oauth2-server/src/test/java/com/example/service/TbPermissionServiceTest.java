package com.example.service;

import com.example.domain.TbPermission;
import com.example.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class TbPermissionServiceTest extends BaseTest {


    @Autowired
    private TbPermissionService tbPermissionService;
    @Test
    public void selectByUserId() {
        List<TbPermission> tbPermissions = tbPermissionService.selectByUserId(37l);
        System.out.println(tbPermissions.size());
    }
}