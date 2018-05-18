package com.pan.trade.user.service.impl;

import com.pan.bean.TradeUser;
import com.pan.bean.TradeUserMoneyLog;
import com.pan.bean.TradeUserMoneyLogExample;
import com.pan.dao.TradeUserMapper;
import com.pan.dao.TradeUserMoneyLogMapper;
import com.pan.trade.common.exception.PanTradeMQException;
import com.pan.trade.common.exception.TradeUserException;
import com.pan.trade.common.protocol.user.ChangeUserMoneyReq;
import com.pan.trade.common.protocol.user.ChangeUserMoneyRes;
import com.pan.trade.common.protocol.user.QueryUserReq;
import com.pan.trade.common.protocol.user.QueryUserRes;
import com.pan.trade.common.tenum.TradeEnum;
import com.pan.trade.user.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Loren on 2018/5/16.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private TradeUserMapper tradeUserMapper;

    @Autowired
    private TradeUserMoneyLogMapper tradeUserMoneyLogMapper;

    /**
     * 根据ID查询用户
     * @param queryUserReq
     * @return
     */
    public QueryUserRes queryUserById(QueryUserReq queryUserReq) {

        QueryUserRes queryUserRes = new QueryUserRes();
        queryUserRes.setCode(TradeEnum.ResultEnum.FAIL.getCode());


        if (queryUserReq == null || StringUtils.isBlank(queryUserReq.getUserId().toString())){
            queryUserRes.setMessage("ID不可为空");
            return queryUserRes;
        }
        TradeUser tradeUser = tradeUserMapper.selectByPrimaryKey(queryUserReq.getUserId());
        if (tradeUser != null){
            BeanUtils.copyProperties(tradeUser,queryUserRes);
            queryUserRes.setCode(TradeEnum.ResultEnum.SUCCESS.getCode());
            queryUserRes.setMessage(TradeEnum.ResultEnum.SUCCESS.getMessage());
            return queryUserRes;
        }else {
            queryUserRes.setCode(TradeEnum.ResultEnum.FAIL.getCode());
            queryUserRes.setMessage("该用户ID数据不存在！");
            return queryUserRes;
        }
    }

    /**
     * 修改用户余额
     * @param changeUserMoneyReq
     * @return
     */
    @Transactional
    public ChangeUserMoneyRes changeUserMoney(ChangeUserMoneyReq changeUserMoneyReq) {
        ChangeUserMoneyRes changeUserMoneyRes = new ChangeUserMoneyRes();
        changeUserMoneyRes.setCode(TradeEnum.ResultEnum.SUCCESS.getCode());
        changeUserMoneyRes.setMessage(TradeEnum.ResultEnum.SUCCESS.getMessage());
        if (changeUserMoneyReq==null || changeUserMoneyReq.getUserId() == null||changeUserMoneyReq.getUserMoney() ==null){
            throw new TradeUserException("请求参数不正确");
        }

        if (changeUserMoneyReq.getUserMoney().compareTo(BigDecimal.ZERO)<=0){
            throw new TradeUserException("金额不能小于0");
        }
        TradeUserMoneyLog tradeUserMoneyLog = new TradeUserMoneyLog();
        tradeUserMoneyLog.setUserId(changeUserMoneyReq.getUserId());
        tradeUserMoneyLog.setUserMoney(changeUserMoneyReq.getUserMoney());
        tradeUserMoneyLog.setOrderId(changeUserMoneyReq.getOrderId());
        tradeUserMoneyLog.setMoneyLogType(changeUserMoneyReq.getMoneyLogType());
        tradeUserMoneyLog.setCreateTime(new Date());
        TradeUser tradeUser = new TradeUser();
        tradeUser.setUserId(changeUserMoneyReq.getUserId());
        tradeUser.setUserMoney(changeUserMoneyReq.getUserMoney());
        //订单付款
        if (changeUserMoneyReq.getMoneyLogType()==Integer.valueOf(TradeEnum.UserMoneyLogTypeStatusEnum.PAID.getCode())){
            TradeUserMoneyLogExample logExample = new TradeUserMoneyLogExample();
            logExample.createCriteria().andUserIdEqualTo(changeUserMoneyReq.getUserId())
                    .andOrderIdEqualTo(changeUserMoneyReq.getOrderId())
                    .andMoneyLogTypeEqualTo(Integer.valueOf(TradeEnum.UserMoneyLogTypeStatusEnum.PAID.getCode()));
            int count = tradeUserMoneyLogMapper.countByExample(logExample);
            if (count > 0){
                throw new TradeUserException("已经付款了，不能继续付款");
            }
            tradeUserMapper.reduceUserMoney(tradeUser);
        }
        //订单退款
        if (changeUserMoneyReq.getMoneyLogType()==Integer.valueOf(TradeEnum.UserMoneyLogTypeStatusEnum.RETURN.getCode())){
            //查询是否有付款记录
            TradeUserMoneyLogExample logExample = new TradeUserMoneyLogExample();
            logExample.createCriteria().andUserIdEqualTo(changeUserMoneyReq.getUserId())
                    .andOrderIdEqualTo(changeUserMoneyReq.getOrderId())
                    .andMoneyLogTypeEqualTo(Integer.valueOf(TradeEnum.UserMoneyLogTypeStatusEnum.PAID.getCode()));
            int count = tradeUserMoneyLogMapper.countByExample(logExample);
            if (count == 0){
                throw new TradeUserException("没有付款信息,不能退款!");
            }
            //防止多次退款
            logExample = new TradeUserMoneyLogExample();
            logExample.createCriteria().andUserIdEqualTo(changeUserMoneyReq.getUserId())
                    .andOrderIdEqualTo(changeUserMoneyReq.getOrderId())
                    .andMoneyLogTypeEqualTo(Integer.valueOf(TradeEnum.UserMoneyLogTypeStatusEnum.RETURN.getCode()));
             count = tradeUserMoneyLogMapper.countByExample(logExample);
            if (count > 0){
                throw new TradeUserException("已经退过款了");
            }
            tradeUserMapper.addUserMoney(tradeUser);
        }
        tradeUserMoneyLogMapper.insert(tradeUserMoneyLog);
        return null;
    }
}
