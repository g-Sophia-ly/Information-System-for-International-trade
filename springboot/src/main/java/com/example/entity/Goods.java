package com.example.entity;

import cn.hutool.core.annotation.Alias;

import java.io.Serializable;

/**
 * 商品表
 */
public class Goods implements Serializable {
    private static final long serialVersionUID = 1L;

    @Alias("ID")
    private Integer id;

    @Alias("商品编号")
    private String goodsNo;

    @Alias("商品属性")
    private String types;

    @Alias("单价")
    private Double price;

    @Alias("面料")
    private String fabric;

    @Alias("内里")
    private String lining;

    @Alias("鞋跟")
    private String heel;

    @Alias("大底")
    private String outsole;

    @Alias("商品图片")
    private String avatar;

    @Alias("下单量")
    private Integer stotal;

    @Alias("下单额")
    private Double ptotal;

    @Alias("状态")
    private String status;

    public Double getPtotal() {
        return ptotal;
    }

    public void setPtotal(Double ptotal) {
        this.ptotal = ptotal;
    }

    public Integer getStotal() {
        return stotal;
    }

    public void setStotal(Integer stotal) {
        this.stotal = stotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }


    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getFabric() {
        return fabric;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public String getLining() {
        return lining;
    }

    public void setLining(String lining) {
        this.lining = lining;
    }

    public String getHeel() {
        return heel;
    }

    public void setHeel(String heel) {
        this.heel = heel;
    }

    public String getOutsole() {
        return outsole;
    }

    public void setOutsole(String outsole) {
        this.outsole = outsole;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}