package com.example.entity;

import cn.hutool.core.annotation.Alias;

import java.io.Serializable;

/**
 * 商品表
 */
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    @Alias("ID")
    private Integer id;

    @Alias("客户ID")
    private Integer customerId;

    @Alias("客户名称")
    private String customerName;

    @Alias("员工ID")
    private Integer staffId;

    @Alias("员工")
    private String staffName;

    @Alias("订单编号")
    private String orderNo;

    @Alias("工厂ID")
    private Integer factoryId;

    @Alias("工厂名称")
    private String factoryName;

    @Alias("商品编号")
    private String goodNo;

    @Alias("颜色")
    private String color;

    @Alias("码段")
    private String size;

    @Alias("单价")
    private Double price;

    @Alias("数量")
    private Integer quantity;

    @Alias("商品图片")
    private String avatar;

    @Alias("工厂交货日期")
    private String date;

    @Alias("特别要求")
    private String notice;

    @Alias("状态")
    private String status;

    @Alias("工厂生产状态")
    private String fstatus;

    @Alias("订单数")
    private Integer trow;

    public String getFstatus() {
        return fstatus;
    }

    public void setFstatus(String fstatus) {
        this.fstatus = fstatus;
    }

    public Integer getExecId() {
        return execId;
    }

    public void setExecId(Integer execId) {
        this.execId = execId;
    }

    private Integer execId;



    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Integer factoryId) {
        this.factoryId = factoryId;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getGoodNo() {
        return goodNo;
    }

    public void setGoodNo(String goodNo) {
        this.goodNo = goodNo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
