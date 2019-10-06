package com.example.service;

import com.example.domain.TbPermission;

import java.util.List;

public interface TbPermissionService{

    default List<TbPermission> selectByUserId(Long userId) {
        return null;
    }
}
