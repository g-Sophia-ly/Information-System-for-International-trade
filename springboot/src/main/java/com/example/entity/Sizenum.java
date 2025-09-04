package com.example.entity;

import cn.hutool.core.annotation.Alias;

import java.io.Serializable;

public class Sizenum implements Serializable {

    @Alias("ID")
    private Integer id;

    @Alias("订单编号")
    private String orderNo;

    @Alias("配码代码")
    private String asscode;

    @Alias("颜色")
    private String color;

    @Alias("36")
    private Integer tsi;

    @Alias("37")
    private Integer tse;

    @Alias("38")
    private Integer te;

    @Alias("39")
    private Integer tn;

    @Alias("40")
    private Integer ft;

    @Alias("41")
    private Integer fo;

    @Alias("42")
    private Integer fw;

    @Alias("商品规格")
    private String unit;

    @Alias("箱数")
    private String boxes;

    @Alias("总数")
    private Integer stotal;

    @Alias("箱号")
    private String boxesNum;

    @Alias("员工ID")
    private Integer staffId;

    @Alias("工厂ID")
    private Integer factoryId;

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Integer factoryId) {
        this.factoryId = factoryId;
    }

    public Integer getTsi() {
        return tsi;
    }

    public void setTsi(Integer tsi) {
        this.tsi = tsi;
    }

    public Integer getTse() {
        return tse;
    }

    public void setTse(Integer tse) {
        this.tse = tse;
    }

    public Integer getTe() {
        return te;
    }

    public void setTe(Integer te) {
        this.te = te;
    }

    public Integer getTn() {
        return tn;
    }

    public void setTn(Integer tn) {
        this.tn = tn;
    }

    public Integer getFt() {
        return ft;
    }

    public void setFt(Integer ft) {
        this.ft = ft;
    }

    public Integer getFo() {
        return fo;
    }

    public void setFo(Integer fo) {
        this.fo = fo;
    }

    public Integer getFw() {
        return fw;
    }

    public void setFw(Integer fw) {
        this.fw = fw;
    }

    public String getAsscode() {
        return asscode;
    }

    public void setAsscode(String asscode) {
        this.asscode = asscode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBoxes() {
        return boxes;
    }

    public void setBoxes(String boxes) {
        this.boxes = boxes;
    }

    public Integer getStotal() {
        return stotal;
    }

    public void setStotal(Integer stotal) {
        this.stotal = stotal;
    }

    public String getBoxesNum() {
        return boxesNum;
    }

    public void setBoxesNum(String boxesNum) {
        this.boxesNum = boxesNum;
    }
}
