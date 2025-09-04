package com.example.mapper;

import com.example.entity.Declare;

import java.util.List;


public interface DeclareMapper {

    /**
     * 新增
     */
    int insert(Declare declare);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Declare declare);

    /**
     * 根据ID查询
     */
    Declare selectById(Integer id);

    /**
     * 查询所有
     */
    List<Declare> selectAll(Declare declare);

}
