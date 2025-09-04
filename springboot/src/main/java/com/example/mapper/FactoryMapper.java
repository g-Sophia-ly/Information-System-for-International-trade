package com.example.mapper;

import com.example.entity.Factory;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FactoryMapper {
    /**
     * 新增
     */
    int insert(Factory factory);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Factory factory);

    /**
     * 根据ID查询
     */
    Factory selectById(Integer id);

    /**
     * 查询所有
     */
    List<Factory> selectAll(Factory factory);

    @Select("select * from factory where username = #{username}")
    Factory selectByUsername(String username);
}
