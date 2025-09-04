package com.example.entity;

import cn.hutool.core.annotation.Alias;

import java.io.Serializable;


public class Ship implements Serializable {
    private static final long serialVersionUID = 1L;
    @Alias("ID")
    private Integer id;

    @Alias("发货地址")
    private String shipper;

    @Alias("收货地址")
    private String consignee;

    @Alias("通知人")
    private String notify;

    @Alias("订单号")
    private String orderNo;

    @Alias("品名")
    private String types;

    @Alias("HS_CODE")
    private String hscode;

    @Alias("启运港")
    private String depature;

    @Alias("到达港")
    private String arrival;

    @Alias("船期")
    private String schedule;

    @Alias("货代")
    private String freight;

    @Alias("箱数")
    private Integer cartons;

    @Alias("双数")
    private Integer prs;

    @Alias("集装箱种类及数量")
    private String containers;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getHscode() {
        return hscode;
    }

    public void setHscode(String hscode) {
        this.hscode = hscode;
    }

    public String getDepature() {
        return depature;
    }

    public void setDepature(String depature) {
        this.depature = depature;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public Integer getCartons() {
        return cartons;
    }

    public void setCartons(Integer cartons) {
        this.cartons = cartons;
    }

    public Integer getPrs() {
        return prs;
    }

    public void setPrs(Integer prs) {
        this.prs = prs;
    }

    public String getContainers() {
        return containers;
    }

    public void setContainers(String containers) {
        this.containers = containers;
    }
}
