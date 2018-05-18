package com.pan.trade.common.protocol.order;

import java.math.BigDecimal;

/**
 * Created by Loren on 2018/5/18.
 */
public class ConfirmOrderReq {

    private Integer userId;
    private String address;
    private String comsignee;
    private Integer goodsId;
    private Integer goodsNumer;
    private String couponId;//优惠券ID
    private BigDecimal moneyPaid;//余额支付
    private BigDecimal goodsPrice;//物品单价
    private BigDecimal shippingFee;//运费
    private BigDecimal orderAmount;//订单总价

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComsignee() {
        return comsignee;
    }

    public void setComsignee(String comsignee) {
        this.comsignee = comsignee;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsNumer() {
        return goodsNumer;
    }

    public void setGoodsNumer(Integer goodsNumer) {
        this.goodsNumer = goodsNumer;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public BigDecimal getMoneyPaid() {
        return moneyPaid;
    }

    public void setMoneyPaid(BigDecimal moneyPaid) {
        this.moneyPaid = moneyPaid;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }
}
