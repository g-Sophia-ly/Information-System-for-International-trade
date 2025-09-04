package com.example.mapper;

import com.example.entity.Sizenum;

import java.util.List;


public interface SizenumMapper {

    /**
     * 新增
     */
    int insert(Sizenum sizenum);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Sizenum sizenum);

    /**
     * 根据ID查询
     */
    Sizenum selectById(Integer id);

    /**
     * 查询所有
     */
    List<Sizenum> selectAll(Sizenum sizenum);

}