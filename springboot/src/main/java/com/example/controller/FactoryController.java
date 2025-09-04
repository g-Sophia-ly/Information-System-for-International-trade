package com.example.controller;


import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.Factory;
import com.example.service.FactoryService;
import com.example.service.LogsService;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

@RestController
@RequestMapping("/factory")
public class FactoryController {

    @Resource
    private FactoryService factoryService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Factory factory) {
        factoryService.add(factory);

        //记录新增工厂日志
        Account currentUser = TokenUtils.getCurrentUser();
        LogsService.recordLog("新增工厂" + factory.getUsername(),"新增",currentUser.getUsername());

        return Result.success();

    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        factoryService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        factoryService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Factory factory) {
        factoryService.updateById(factory);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Factory factory = factoryService.selectById(id);
        return Result.success(factory);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Factory factory) {
        List<Factory> list = factoryService.selectAll(factory);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Factory factory,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Factory> page = factoryService.selectPage(factory, pageNum, pageSize);
        return Result.success(page);
    }



}

