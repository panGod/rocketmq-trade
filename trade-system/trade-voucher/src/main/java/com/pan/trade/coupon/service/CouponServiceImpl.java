package com.pan.trade.coupon.service;

import com.pan.bean.TradeCoupon;
import com.pan.dao.TradeCouponMapper;
import com.pan.trade.common.protocol.voucher.ChangeCouponStatusReq;
import com.pan.trade.common.protocol.voucher.ChangeCouponStatusRes;
import com.pan.trade.common.protocol.voucher.QueryCouponReq;
import com.pan.trade.common.protocol.voucher.QueryCouponRes;
import com.pan.trade.common.tenum.TradeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * Created by Loren on 2018/5/18.
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private TradeCouponMapper tradeCouponMapper;

    /**
     * 查询优惠券
     * @param queryCouponReq
     * @return
     */
    public QueryCouponRes queryCoupon(QueryCouponReq queryCouponReq) {
        QueryCouponRes queryCouponRes = new QueryCouponRes();
        queryCouponRes.setCode(TradeEnum.ResultEnum.SUCCESS.getCode());
        queryCouponRes.setMessage(TradeEnum.ResultEnum.SUCCESS.getMessage());
        try {
            if (queryCouponReq == null|| StringUtils.isBlank(queryCouponReq.getCouponId())){
                throw new Exception("请求参数不正确,优惠券编号为空！");
            }
            TradeCoupon tradeCoupon = tradeCouponMapper.selectByPrimaryKey(queryCouponReq.getCouponId());
            if (tradeCoupon != null){
                BeanUtils.copyProperties(tradeCoupon,queryCouponRes);
                queryCouponRes.setCode(TradeEnum.ResultEnum.SUCCESS.getCode());
                queryCouponRes.setMessage(TradeEnum.ResultEnum.SUCCESS.getMessage());
            }else {
                throw new Exception("未查到该优惠券!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 改变优惠券状态
     * @param changeCouponStatusReq
     * @return
     */
    public ChangeCouponStatusRes changeCouponStatus(ChangeCouponStatusReq changeCouponStatusReq) {
        ChangeCouponStatusRes changeCouponStatusRes = new ChangeCouponStatusRes();
        changeCouponStatusRes.setCode(TradeEnum.ResultEnum.SUCCESS.getCode());
        changeCouponStatusRes.setMessage(TradeEnum.ResultEnum.SUCCESS.getMessage());
        try {
            if (changeCouponStatusReq == null || StringUtils.isBlank(changeCouponStatusReq.getCouponId())){
                throw new Exception("请求参数不正确，优惠券编号为空");
            }
            TradeCoupon tradeCoupon = new TradeCoupon();
            tradeCoupon.setCouponId(changeCouponStatusReq.getCouponId());
            tradeCoupon.setOrderId(changeCouponStatusReq.getOrderId());
            //使用优惠券
            if (changeCouponStatusReq.getIsUsed().equals(TradeEnum.CouponYESORNOStatusEnum.YES.getCode())){

                int i = tradeCouponMapper.useCoupon(tradeCoupon);
                if (i<=0){
                    throw new Exception("使用该优惠券失败！");
                }
            }else if (changeCouponStatusReq.getIsUsed().equals(TradeEnum.CouponYESORNOStatusEnum.NO.getCode())){
                tradeCouponMapper.useCoupon(tradeCoupon);

            }
        } catch (Exception e) {
            changeCouponStatusRes.setCode(TradeEnum.ResultEnum.FAIL.getCode());
            changeCouponStatusRes.setMessage(e.getMessage());
        }
        return changeCouponStatusRes;
    }
}
