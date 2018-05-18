package com.pan.trade.common.rocketmq;

import com.pan.trade.common.rocketmq.IMessageProcessor;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * Created by Loren on 2018/5/16.
 */
public class TestProcessor implements IMessageProcessor {
    public boolean handleMessage(MessageExt messageExt) {
        System.out.println("收到消息:"+messageExt.toString());
        return true;
    }
}
