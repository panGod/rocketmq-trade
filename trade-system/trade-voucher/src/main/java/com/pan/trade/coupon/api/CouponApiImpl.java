package com.pan.trade.coupon.api;

import com.pan.trade.common.api.ICouponApi;
import com.pan.trade.common.protocol.voucher.ChangeCouponStatusReq;
import com.pan.trade.common.protocol.voucher.ChangeCouponStatusRes;
import com.pan.trade.common.protocol.voucher.QueryCouponReq;
import com.pan.trade.common.protocol.voucher.QueryCouponRes;
import com.pan.trade.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Loren on 2018/5/18.
 */
@Controller
public class CouponApiImpl implements ICouponApi {

    @Autowired
    private CouponService couponService;

    @RequestMapping("/queryCoupon")
    @ResponseBody
    public QueryCouponRes queryCoupon(@RequestBody QueryCouponReq queryCouponReq) {
        return couponService.queryCoupon(queryCouponReq);
    }
    @RequestMapping("/changeCouponStatus")
    @ResponseBody
    public ChangeCouponStatusRes changeCouponStatus(@RequestBody ChangeCouponStatusReq changeCouponStatusReq) {
        return couponService.changeCouponStatus(changeCouponStatusReq );
    }
}
