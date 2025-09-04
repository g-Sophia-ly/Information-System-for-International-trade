package com.example.service;


import com.example.entity.Declare;
import com.example.mapper.DeclareMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单表业务处理
 **/
@Service
public class DeclareService {

    @Resource
    private DeclareMapper declareMapper;

    /**
     * 新增
     */
    public void add(Declare declare) {
        declareMapper.insert(declare);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        declareMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            declareMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Declare declare) {
        declareMapper.updateById(declare);
    }

    /**
     * 根据ID查询
     */
    public Declare selectById(Integer id) {
        return declareMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Declare> selectAll(Declare declare) {
        return declareMapper.selectAll(declare);
    }

    /**
     * 分页查询
     */
    public PageInfo<Declare> selectPage(Declare declare, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Declare> list = declareMapper.selectAll(declare);
        return PageInfo.of(list);
    }
}

