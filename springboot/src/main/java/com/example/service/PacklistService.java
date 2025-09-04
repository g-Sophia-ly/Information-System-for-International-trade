package com.example.service;


import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Packlist;
import com.example.exception.CustomException;
import com.example.mapper.PacklistMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单表业务处理
 **/
@Service
public class PacklistService {

    @Resource
    private PacklistMapper packlistMapper;

    /**
     * 新增
     */
    public void add(Packlist packlist) {
        packlistMapper.insert(packlist);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        packlistMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            packlistMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Packlist packlist) {
        packlistMapper.updateById(packlist);
    }

    /**
     * 根据ID查询
     */
    public Packlist selectById(Integer id) {
        return packlistMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Packlist> selectAll(Packlist packlist) {
        return packlistMapper.selectAll(packlist);
    }

    /**
     * 分页查询
     */
    public PageInfo<Packlist> selectPage(Packlist packlist, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Packlist> list = packlistMapper.selectAll(packlist);
        return PageInfo.of(list);
    }
}

