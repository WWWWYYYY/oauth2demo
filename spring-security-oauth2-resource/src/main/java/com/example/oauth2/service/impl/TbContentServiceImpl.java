package com.example.oauth2.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.example.oauth2.mapper.TbContentMapper;
import com.example.oauth2.service.TbContentService;
@Service
public class TbContentServiceImpl implements TbContentService{

    @Resource
    private TbContentMapper tbContentMapper;

}
