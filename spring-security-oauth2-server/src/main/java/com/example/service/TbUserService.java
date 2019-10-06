package com.example.service;

import com.example.domain.TbUser;

public interface TbUserService{


    TbUser selectOneTbUserByUsername(String username);
    TbUser getByUsername(String username);
}
