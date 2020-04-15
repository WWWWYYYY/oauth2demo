package com.example.oauth2.domain;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SecurityConfig.
 */
@Data
@Table(name = "security_config")
public class SecurityConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pattern")
    private String pattern;

    @Column(name = "type")
    private String type;

    @Column(name = "sort")
    private Integer sort;

}
