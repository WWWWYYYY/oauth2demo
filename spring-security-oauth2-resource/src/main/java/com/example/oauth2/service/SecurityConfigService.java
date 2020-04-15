package com.example.oauth2.service;

import com.example.oauth2.domain.SecurityConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing SecurityConfig.
 */

public interface SecurityConfigService {



    List<SecurityConfig> findAll();



}
