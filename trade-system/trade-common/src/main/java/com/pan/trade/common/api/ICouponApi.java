package com.pan.trade.common.api;

import com.pan.trade.common.protocol.order.ConfirmOrderReq;
import com.pan.trade.common.protocol.order.ConfirmOrderRes;
import com.pan.trade.common.protocol.voucher.ChangeCouponStatusReq;
import com.pan.trade.common.protocol.voucher.ChangeCouponStatusRes;
import com.pan.trade.common.protocol.voucher.QueryCouponReq;
import com.pan.trade.common.protocol.voucher.QueryCouponRes;

/**
 * Created by Loren on 2018/5/16.
 */
public interface ICouponApi {

     QueryCouponRes queryCoupon(QueryCouponReq queryCouponReq);

     ChangeCouponStatusRes changeCouponStatus(ChangeCouponStatusReq changeCouponStatusReq);


}
