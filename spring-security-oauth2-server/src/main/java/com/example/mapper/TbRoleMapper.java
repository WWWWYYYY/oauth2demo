package com.example.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.example.domain.TbRole;
import tk.mybatis.mapper.MyMapper;

public interface TbRoleMapper extends MyMapper<TbRole> {
    List<TbRole> selectAllByEnname(@Param("enname")String enname);






}