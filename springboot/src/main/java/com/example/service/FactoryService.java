package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.entity.Factory;
import com.example.entity.Staff;
import com.example.exception.CustomException;
import com.example.mapper.FactoryMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FactoryService {

    @Resource
    private FactoryMapper factoryMapper;

    /**
     * 新增
     */
    public void add(Factory factory) {
        Factory dbFactory = factoryMapper.selectByUsername(factory.getUsername());
        if (ObjectUtil.isNotNull(dbFactory)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(factory.getPassword())) {
            factory.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        if (ObjectUtil.isEmpty(factory.getName())) {
            factory.setName(factory.getUsername());
        }
        factory.setRole(RoleEnum.FACTORY.name());
        factoryMapper.insert(factory);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        factoryMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            factoryMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Factory factory) {
        factoryMapper.updateById(factory);
    }

    /**
     * 根据ID查询
     */
    public Factory selectById(Integer id) {
        return factoryMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Factory> selectAll(Factory factory) {
        return factoryMapper.selectAll(factory);
    }

    /**
     * 分页查询
     */
    public PageInfo<Factory> selectPage(Factory factory, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Factory> list = factoryMapper.selectAll(factory);
        return PageInfo.of(list);
    }

    public Account login(Account account) {
        Account dbFactory = factoryMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbFactory)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (dbFactory.getStatus()){ //如果是禁用，不允许登录
            throw new CustomException(ResultCodeEnum.NO_AUTH);
        }
        if (!account.getPassword().equals(dbFactory.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token
        String tokenData = dbFactory.getId() + "-" + RoleEnum.FACTORY.name();
        String token = TokenUtils.createToken(tokenData, dbFactory.getPassword());
        dbFactory.setToken(token);
        return dbFactory;
    }

    /**
     * 注册
     */
    public void register(Account account) {
        Factory factory = new Factory();
        BeanUtils.copyProperties(account, factory);
        this.add(factory);
    }

    public void updatePassword(Account account) {
        Factory factory = factoryMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(factory)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(factory.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        factory.setPassword(account.getNewPassword());
        factoryMapper.updateById(factory);
    }
}

