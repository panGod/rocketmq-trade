package com.pan.trade.common.rocketmq;

import com.pan.trade.common.exception.PanTradeMQException;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Loren on 2018/5/16.
 */
public class PanPushConsumer {

    public static  final Logger logger = LoggerFactory.getLogger(PanProducer.class);




    private String groupName;
    private String topic;
    private String namesrvAddr;
    private String tags="*";//默认订阅该主题下的所有的tags
    private int maxConsumeThread=64;



    private IMessageProcessor processor;


    public void init(){
        if (StringUtils.isBlank(this.groupName)){
            throw  new PanTradeMQException("groupName is blank");
        }
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        try {
            consumer.subscribe(this.topic,this.tags);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);//从头开始订阅
            consumer.setNamesrvAddr(this.namesrvAddr);
            consumer.setConsumeThreadMax(this.maxConsumeThread);
            PanMessageListener listener = new PanMessageListener();
            listener.setMessageProcessor(this.processor);
            consumer.registerMessageListener(listener);
            consumer.start();
            logger.info("consumer is starting....");
        } catch (MQClientException e) {
            logger.error("consumer error:{}",e);
            throw new PanTradeMQException(e);
        }
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public void setProcessor(IMessageProcessor processor) {
        this.processor = processor;
    }
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
