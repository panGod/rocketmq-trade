package com.pan.trade.common.rocketmq;

import org.apache.rocketmq.common.message.MessageExt;

/**
 * Created by Loren on 2018/5/16.
 */
public interface IMessageProcessor {

    /**
     * 处理消息
     * @param messageExt
     * @return
     */
    boolean handleMessage(MessageExt messageExt);

}
