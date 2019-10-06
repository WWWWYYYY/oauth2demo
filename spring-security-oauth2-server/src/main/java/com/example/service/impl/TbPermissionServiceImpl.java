package com.example.service.impl;

import com.example.domain.TbPermission;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.example.mapper.TbPermissionMapper;
import com.example.service.TbPermissionService;

import java.util.List;

@Service
public class TbPermissionServiceImpl implements TbPermissionService{

    @Resource
    private TbPermissionMapper tbPermissionMapper;


    @Override
    public List<TbPermission> selectByUserId(Long userId) {
        return tbPermissionMapper.selectByUserId(userId);
    }
}
