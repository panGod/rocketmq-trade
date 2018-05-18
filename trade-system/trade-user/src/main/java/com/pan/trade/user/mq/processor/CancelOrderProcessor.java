package com.pan.trade.user.mq.processor;

import com.pan.trade.common.rocketmq.IMessageProcessor;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;

/**
 * Created by Loren on 2018/5/16.
 */
public class CancelOrderProcessor implements IMessageProcessor {
    public boolean handleMessage(MessageExt messageExt) {
        try {
            String body = new String(messageExt.getBody(),"UTF-8");
            String msgId = messageExt.getMsgId();
            String tags = messageExt.getTags();
            String keys = messageExt.getKeys();
        } catch (UnsupportedEncodingException e) {
            return false;
        }
        return true;
    }
}
