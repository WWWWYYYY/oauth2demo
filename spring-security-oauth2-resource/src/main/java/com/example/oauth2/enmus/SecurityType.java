package com.example.oauth2.enmus;

import java.util.HashMap;
import java.util.Map;

/**
 * 网关权限过滤类型
 *
 * @Author tanglh
 * @Date 2019/1/16 16:08
 */
public enum SecurityType {
    PERMIT_ALL("permitAll", "无需过滤"),
    AUTHENTICATED("authenticated", "需要过滤"),;

    private static Map<String, SecurityType> typeMap;

    static {
        typeMap = new HashMap<>();
        for (SecurityType type : SecurityType.values()) {
            typeMap.putIfAbsent(type.getType(), type);
        }
    }
    SecurityType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    private String type;

    private String name;

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }


    public static SecurityType fromType(String type) {
        return typeMap.get(type);
    }

}
