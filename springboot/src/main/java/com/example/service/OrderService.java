package com.example.service;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import com.example.common.Result;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Goods;
import com.example.entity.Order;
import com.example.mapper.OrderMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单表业务处理
 **/
@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private GoodsService goodsService;

    /**
     * 新增
     */
    public void add(Order order) {
        orderMapper.insert(order);

        Goods goods = goodsService.selectByGoodsNo(order.getGoodNo());
        //总下单量
        int num = order.getQuantity() + goods.getStotal();
        goods.setStotal(num);
        //总下单额
        double tprice = (order.getQuantity() * order.getPrice()) + goods.getPtotal();
        goods.setPtotal(tprice);
        goodsService.updateById(goods);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id, Order order) {
        orderMapper.deleteById(id);
        Goods goods = goodsService.selectByGoodsNo(order.getGoodNo());
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            orderMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Order order) {
        orderMapper.updateById(order);
    }

    /**
     * 根据ID查询
     */
    public Order selectById(Integer id) {
        return orderMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Order> selectAll(Order order) {
        return orderMapper.selectAll(order);
    }

    /**
     * 分页查询
     */
    public PageInfo<Order> selectPage(Order order, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.STAFF.name().equals(currentUser.getRole())) {
            order.setStaffId(currentUser.getId());
        }
        if (RoleEnum.FACTORY.name().equals(currentUser.getRole())) {
            order.setFactoryId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Order> list = orderMapper.selectAll(order);
        return PageInfo.of(list);
    }

    public Order selectByOrderId(String orderId) {
        return orderMapper.selectByOrderId(orderId);
    }

    public void updateExec(String orderNo, Integer execId) {
        int i = orderMapper.updateByOrderNo(orderNo, execId);
    }


    public List<Order> selectAllByStaffId(Integer staffId) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getUsername().equals("admin")) {
            Order order = new Order();
            return orderMapper.selectAll(order);
        } else {
            return orderMapper.selectAllByStaffId(staffId);
        }
    }

    public void updateStatus(String orderNo) {
        orderMapper.updateStatus(orderNo);
    }

    public Order checkProdSta(String orderNo) {
        return orderMapper.checkProdSta(orderNo);
    }

    public List<Dict> selectPie() {
        List<Dict> list = new ArrayList<>();
        List<Order> orderList = orderMapper.selectAll(null);
        List<String> nameList = orderList.stream().map(Order::getCustomerName).collect(Collectors.toList());
        List <String> nameList2 = new ArrayList<>();

        //客户名称去重复
        Map<String,String> map = new HashMap<>();
        for (String str : nameList) {
        map.put(str, str);
    }
        Set<String> sets = map.keySet();
        for (String string : sets) {
            nameList2.add(string);
        }
        for (String name : nameList2) {
            Dict dict = Dict.create();
            Integer sum1 = orderList.stream().filter(order -> order.getCustomerName().equals(name)).map(Order::getQuantity).reduce(Integer::sum).orElse(0);
            Double sum2 = orderList.stream().filter(order -> order.getCustomerName().equals(name)).map(Order::getPrice).reduce(Double::sum).orElse(0D);
            Double sum = sum1 * sum2;
            dict.set("name", name).set("value", sum);
            list.add(dict);
        }
        return list;
    }




}


