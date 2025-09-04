package com.example.service;


import com.example.entity.Ship;
import com.example.mapper.ShipMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShipService {

    @Resource
    private ShipMapper shipMapper;

    /**
     * 新增
     */
    public void add(Ship ship) {
        shipMapper.insert(ship);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        shipMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            shipMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Ship ship) {
        shipMapper.updateById(ship);
    }

    /**
     * 根据ID查询
     */
    public Ship selectById(Integer id) {
        return shipMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Ship> selectAll(Ship ship) {
        return shipMapper.selectAll(ship);
    }

    /**
     * 分页查询
     */
    public PageInfo<Ship> selectPage(Ship ship, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Ship> list = shipMapper.selectAll(ship);
        return PageInfo.of(list);
    }
}

