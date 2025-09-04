package com.example.controller;


import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;


import com.example.entity.Goods;
import com.example.entity.Order;
import com.example.exception.CustomException;
import com.example.mapper.OrderMapper;
import com.example.service.*;


import com.example.utils.ValidateCodeCache;
import com.example.utils.ValidateCodeProperties;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 基础前端接口
 */
@RestController
public class WebController {

    @Resource
    private AdminService adminService;

    @Resource
    private StaffService staffService;

    @Resource
    private FactoryService factoryService;

    @Resource
    private OrderService orderService;

    @Resource
    private GoodsService goodsService;

    @GetMapping("/")
    public Result hello() {
        return Result.success("访问成功");
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody Account account) {  //可能有多种角色（account包含所有类）
        if (ObjectUtil.isEmpty(account.getUsername()) || ObjectUtil.isEmpty(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole()) || ObjectUtil.isEmpty(account.getKey()) || ObjectUtil.isEmpty(account.getCode())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        //对验证码进行校验
        boolean result = ValidateCodeCache.validateCode(account.getKey(), account.getCode());
        if (!result) {
            return Result.error(ResultCodeEnum.VALIDATE_CODE_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) { //判断是否是admin
            account = adminService.login(account);
        } else if (RoleEnum.STAFF.name().equals(account.getRole())) {
            account = staffService.login(account);
        } else {
            account = factoryService.login(account);
        }
        //记录登录日志
        LogsService.recordLog("系统登录","登录",account.getUsername());
        return Result.success(account);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.STAFF.name().equals(account.getRole())) {
            staffService.register(account);
            //记录注册日志
            LogsService.recordLog("员工注册","注册",account.getUsername());
        } else if (RoleEnum.FACTORY.name().equals(account.getRole())) {
            factoryService.register(account);
            //记录注册日志
            LogsService.recordLog("工厂注册","注册",account.getUsername());
        }
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getNewPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            adminService.updatePassword(account);
        } else if (RoleEnum.STAFF.name().equals(account.getRole())) {
            staffService.updatePassword(account);
        } else {
            factoryService.updatePassword(account);
        }
        return Result.success();
    }

    @GetMapping("/validateCode")
    public void getValidateCode(HttpServletRequest request, HttpServletResponse response) {
        // 生成验证码
        ValidateCodeProperties code = new ValidateCodeProperties();
        setHeader(response, code.getType());
        Captcha captcha = createCaptcha(code);
        // 存储验证码到缓存
        ValidateCodeCache.setCache(request.getParameter("key"), captcha.text().toLowerCase());
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            captcha.out(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(ResultCodeEnum.VALIDATE_CODE_ERROR);
        }
    }

    /**
     * 创建验证码
     */
    private Captcha createCaptcha(ValidateCodeProperties code) {
        Captcha captcha = null;
        if ("gif".equalsIgnoreCase(code.getType())) {
            captcha = new GifCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        } else {
            captcha = new SpecCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        }
        captcha.setCharType(code.getCharType());
        return captcha;
    }

    /**
     * 设置验证码返回头
     */
    private void setHeader(HttpServletResponse response, String type) {
        if ("gif".equalsIgnoreCase(type)) {
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        } else {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    }

    /**
     * 统计顶端三个数据
     */
    @GetMapping("/dataCount")
    public Result dataCount() {
        List<Goods> goodsList = goodsService.selectAll(null);
        List<Order> orderList = orderService.selectAll(null);
        // 查询商品总下单量
        Integer num = goodsList.stream().map(Goods::getStotal).reduce(Integer::sum).orElse(0);
        // 查询商品总下单额
        Double money = goodsList.stream().map(Goods::getPtotal).reduce(Double::sum).orElse(0D);
        // 查询总订单量
        Long orders = (orderList.stream().map(Order::getOrderNo)).count();
        Dict dict = Dict.create().set("num", num).set("money", money).set("orders", orders);
        return Result.success(dict);
    }

    /**
     * 统计总下单量柱状图
     */
    @GetMapping("/goodsCount")
    public Result goodsCount() {
        List<Dict> list = new ArrayList<>();
        List<Goods> goodsList = goodsService.selectAll(null);
        List<String> nameList = goodsList.stream().map(Goods::getGoodsNo).collect(Collectors.toList());
        for (String name : nameList) {
            Dict dict = Dict.create();
            Integer num = goodsList.stream().filter(goods -> goods.getGoodsNo().equals(name)).map(Goods::getStotal).reduce(Integer::sum).orElse(0);
            dict.set("name", name).set("value", num);
            list.add(dict);
        }
        return Result.success(list);
    }

    /**
     * 统计总下单额柱状图
     */
    @GetMapping("/goodsCount1")
    public Result goodsCount1() {
        List<Dict> list = new ArrayList<>();
        List<Goods> goodsList = goodsService.selectAll(null);
        List<String> nameList = goodsList.stream().map(Goods::getGoodsNo).collect(Collectors.toList());
        for (String name : nameList) {
            Dict dict = Dict.create();
            Double money = goodsList.stream().filter(goods -> goods.getGoodsNo().equals(name)).map(Goods::getPtotal).reduce(Double::sum).orElse(0D);
            dict.set("name", name).set("value", money);
            list.add(dict);
        }
        return Result.success(list);
    }

    @Resource
    private OrderMapper orderMapper;

    @GetMapping("/getOption")
    public Result getOption() {
        List<Map<String, Object>> totalSalesForCurrentYear = orderMapper.getTotalSalesForCurrentYear();

        List<Map<String, Object>> formattedData = new ArrayList<>();
        for (Map<String, Object> entry : totalSalesForCurrentYear) {
            Map<String, Object> formattedEntry = new HashMap<>();
            formattedEntry.put("name", entry.get("name"));
            formattedEntry.put("type", "line");
            formattedEntry.put("stack", "Total");

            List<Double> monthlySales = new ArrayList<>();
            for (int i = 1; i <= 12; i++) { // Loop through each month
                boolean found = false;
                for (Map<String, Object> data : totalSalesForCurrentYear) {
                    if (data.get("sales_month").equals(i) && data.get("name").equals(entry.get("name"))) {
                        monthlySales.add((Double) data.get("total_sales"));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    monthlySales.add((double) 0); // If no data found for the month, add zero
                }
            }

            formattedEntry.put("data", monthlySales);
            formattedData.add(formattedEntry);
        }

        List<Map<String, Object>> formattedData1 = new ArrayList<>();

        Map<String, Boolean> nameMap = new HashMap<>();

        for (Map<String, Object> entry : formattedData) {
            String name = (String) entry.get("name");
            if (!nameMap.containsKey(name)) {
                formattedData1.add(entry);
                nameMap.put(name, true);
            }
        }
        return Result.success(formattedData1);
    }


}

