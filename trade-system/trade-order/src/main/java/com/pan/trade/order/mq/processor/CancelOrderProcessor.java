package com.pan.trade.order.mq.processor;

import com.alibaba.fastjson.JSON;
import com.pan.bean.TradeOrder;
import com.pan.dao.TradeOrderMapper;
import com.pan.trade.common.protocol.mq.CancelOrderMQ;
import com.pan.trade.common.rocketmq.IMessageProcessor;
import com.pan.trade.common.tenum.TradeEnum;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;

/**
 * Created by Loren on 2018/5/16.
 */
public class CancelOrderProcessor implements IMessageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(CancelOrderProcessor.class);

    @Autowired
    private TradeOrderMapper tradeOrderMapper;

    public boolean handleMessage(MessageExt messageExt) {
        try {
            String body = new String(messageExt.getBody(),"UTF-8");
            String msgId = messageExt.getMsgId();
            String tags = messageExt.getTags();
            String keys = messageExt.getKeys();
            CancelOrderMQ cancelOrderMQ = JSON.parseObject(body,CancelOrderMQ.class);
            logger.info("order CancelOrdermessage:"+body);
            TradeOrder tradeOrder = new TradeOrder();
            tradeOrder.setOrderId(cancelOrderMQ.getOrderId());
            tradeOrder.setOrderStatus(TradeEnum.OrderStatusEnum.CANCEL.getCode());
            tradeOrderMapper.updateByPrimaryKeySelective(tradeOrder);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
