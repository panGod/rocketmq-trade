package com.pan.trade.coupon.mq.processor;

import com.alibaba.fastjson.JSON;
import com.pan.trade.common.protocol.mq.CancelOrderMQ;
import com.pan.trade.common.protocol.user.ChangeUserMoneyReq;
import com.pan.trade.common.protocol.voucher.ChangeCouponStatusReq;
import com.pan.trade.common.rocketmq.IMessageProcessor;
import com.pan.trade.common.tenum.TradeEnum;
import com.pan.trade.coupon.service.CouponService;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Created by Loren on 2018/5/16.
 */
public class CancelOrderProcessor implements IMessageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(CancelOrderProcessor.class);

    @Autowired
    private CouponService couponService;

    public boolean handleMessage(MessageExt messageExt) {
        try {
            String body = new String(messageExt.getBody(),"UTF-8");
            String msgId = messageExt.getMsgId();
            String tags = messageExt.getTags();
            String keys = messageExt.getKeys();
            CancelOrderMQ cancelOrderMQ = JSON.parseObject(body,CancelOrderMQ.class);
            logger.info("coupon CancelOrdermessage:"+body);
            if (StringUtils.isNotBlank(cancelOrderMQ.getCouponId())){
                ChangeCouponStatusReq changeCouponStatusReq = new ChangeCouponStatusReq();
                changeCouponStatusReq.setOrderId(cancelOrderMQ.getOrderId());
                changeCouponStatusReq.setCouponId(cancelOrderMQ.getCouponId());
                changeCouponStatusReq.setIsUsed(TradeEnum.CouponYESORNOStatusEnum.NO.getCode());
                couponService.changeCouponStatus(changeCouponStatusReq);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
