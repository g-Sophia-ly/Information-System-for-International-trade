package com.example.entity;

import cn.hutool.core.annotation.Alias;

import java.io.Serializable;


public class Packlist implements Serializable {
    private static final long serialVersionUID = 1L;
    @Alias("ID")
    private Integer id;

    @Alias("客户ID")
    private Integer customerId;

    @Alias("客户名称")
    private String customerName;

    @Alias("港口")
    private String fromcus;

    @Alias("合同号")
    private String inv;

    @Alias("日期")
    private String todate;

    @Alias("鞋子")
    private String shoes;

    @Alias("总数")
    private Integer prs;

    @Alias("集装箱数")
    private Integer ctns;

    @Alias("净重")
    private Double netw;

    @Alias("毛重")
    private Double gross;

    @Alias("立方米")
    private Double cbm;

    public Double getNetw() {
        return netw;
    }

    public void setNetw(Double netw) {
        this.netw = netw;
    }

    public Double getGross() {
        return gross;
    }

    public void setGross(Double gross) {
        this.gross = gross;
    }

    public Double getCbm() {
        return cbm;
    }

    public void setCbm(Double cbm) {
        this.cbm = cbm;
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

    public String getFromcus() {
        return fromcus;
    }

    public void setFromcus(String fromcus) {
        this.fromcus = fromcus;
    }

    public String getInv() {
        return inv;
    }

    public void setInv(String inv) {
        this.inv = inv;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getShoes() {
        return shoes;
    }

    public void setShoes(String shoes) {
        this.shoes = shoes;
    }

    public Integer getPrs() {
        return prs;
    }

    public void setPrs(Integer prs) {
        this.prs = prs;
    }

    public Integer getCtns() {
        return ctns;
    }

    public void setCtns(Integer ctns) {
        this.ctns = ctns;
    }

}
