package com.example.entity;

import cn.hutool.core.annotation.Alias;

import java.io.Serializable;


public class Declare implements Serializable {
    private static final long serialVersionUID = 1L;
    @Alias("ID")
    private Integer id;

    @Alias("合同号")
    private String inv;

    @Alias("HS_CODE")
    private String hscode;

    @Alias("商品名称")
    private String types;

    @Alias("数量")
    private Integer quantity;

    @Alias("单价")
    private String price;

    @Alias("总价")
    private String totalPrice;

    @Alias("原产国")
    private String fromCountry;

    @Alias("最终目的地")
    private String toCountry;

    @Alias("境内货源地")
    private String goodplace;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInv() {
        return inv;
    }

    public void setInv(String inv) {
        this.inv = inv;
    }

    public String getHscode() {
        return hscode;
    }

    public void setHscode(String hscode) {
        this.hscode = hscode;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }

    public String getToCountry() {
        return toCountry;
    }

    public void setToCountry(String toCountry) {
        this.toCountry = toCountry;
    }

    public String getGoodplace() {
        return goodplace;
    }

    public void setGoodplace(String goodplace) {
        this.goodplace = goodplace;
    }
}
