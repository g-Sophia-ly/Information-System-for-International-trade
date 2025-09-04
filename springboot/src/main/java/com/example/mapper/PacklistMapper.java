package com.example.mapper;

import com.example.entity.Packlist;

import java.util.List;


public interface PacklistMapper {

    /**
     * 新增
     */
    int insert(Packlist packlist);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Packlist packlist);

    /**
     * 根据ID查询
     */
    Packlist selectById(Integer id);

    /**
     * 查询所有
     */
    List<Packlist> selectAll(Packlist packlist);

}
