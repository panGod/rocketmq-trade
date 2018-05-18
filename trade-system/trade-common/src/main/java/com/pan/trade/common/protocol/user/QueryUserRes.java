package com.pan.trade.common.protocol.user;

import com.pan.trade.common.protocol.base.BaseRes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Loren on 2018/5/16.
 */
public class QueryUserRes extends BaseRes{
    @Override
    public String toString() {
        return "QueryUserRes{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", userScore=" + userScore +
                ", userRegTime=" + userRegTime +
                ", userMoney=" + userMoney +
                '}';
    }

    private Integer userId;

    private String userName;

    private String userMobile;

    private Integer userScore;

    private Date userRegTime;

    private BigDecimal userMoney;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public Integer getUserScore() {
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    public Date getUserRegTime() {
        return userRegTime;
    }

    public void setUserRegTime(Date userRegTime) {
        this.userRegTime = userRegTime;
    }

    public BigDecimal getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(BigDecimal userMoney) {
        this.userMoney = userMoney;
    }
}
