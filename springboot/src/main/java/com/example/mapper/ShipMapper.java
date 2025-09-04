package com.example.mapper;

import com.example.entity.Ship;

import java.util.List;


public interface ShipMapper {

    /**
     * 新增
     */
    int insert(Ship ship);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Ship ship);

    /**
     * 根据ID查询
     */
    Ship selectById(Integer id);

    /**
     * 查询所有
     */
    List<Ship> selectAll(Ship ship);

}
