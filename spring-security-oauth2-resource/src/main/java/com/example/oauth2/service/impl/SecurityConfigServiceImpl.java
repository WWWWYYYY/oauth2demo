package com.example.oauth2.service.impl;

import com.example.oauth2.domain.SecurityConfig;
import com.example.oauth2.mapper.SecurityConfigMapper;
import com.example.oauth2.service.SecurityConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限
 */
@Service
public class SecurityConfigServiceImpl implements SecurityConfigService {

    @Autowired
    private SecurityConfigMapper securityConfigMapper;
    @Override
    public List<SecurityConfig> findAll() {
        return securityConfigMapper.selectAll();
    }
}
