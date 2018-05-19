package com.pan.trade.coupon.service;

import com.pan.trade.common.protocol.voucher.ChangeCouponStatusReq;
import com.pan.trade.common.protocol.voucher.ChangeCouponStatusRes;
import com.pan.trade.common.protocol.voucher.QueryCouponReq;
import com.pan.trade.common.protocol.voucher.QueryCouponRes;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Loren on 2018/5/18.
 */
public interface CouponService {

     QueryCouponRes queryCoupon(@RequestBody QueryCouponReq queryCouponReq);

     ChangeCouponStatusRes changeCouponStatus(ChangeCouponStatusReq changeCouponStatusReq);

}
