package com.pan.trade.common.rocketmq;

import com.pan.trade.common.exception.PanTradeMQException;
import com.pan.trade.common.tenum.TradeEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Loren on 2018/5/16.
 */
public class PanProducer {

    public static  final Logger logger = LoggerFactory.getLogger(PanProducer.class);


    private String groupName;
    private String namesrvAddr;
    private DefaultMQProducer producer;
    private int maxMessageSize = 1024*1024*4; //4m
    private int sendMsgTimeout = 10000;

    public void init(){
        if (StringUtils.isBlank(this.groupName)){
            throw  new PanTradeMQException("groupName is blank");
        }
        if (StringUtils.isBlank(this.namesrvAddr)){
            throw  new PanTradeMQException("namesrvAddr is blank");
        }
        this.producer = new DefaultMQProducer(groupName);
        this.producer.setNamesrvAddr(namesrvAddr);
        this.producer.setMaxMessageSize(maxMessageSize);
        this.producer.setSendMsgTimeout(sendMsgTimeout);
        try {
            this.producer.start();
            logger.info("create producer start.....");
        } catch (MQClientException e) {
            logger.error("create producer error.....");
            throw new PanTradeMQException(e);
        }
    }

    public SendResult send(String topic,String tags,String keys,String messageText){

        if (StringUtils.isBlank(topic)){
            throw  new PanTradeMQException("topic is blank");
        }
        if (StringUtils.isBlank(messageText)){
            throw  new PanTradeMQException("messageText is blank");
        }

        Message message = new Message(topic,tags,keys,messageText.getBytes());
        SendResult send ;
        try {
            send = this.producer.send(message);
        } catch (Exception e) {
            logger.error("send message error.....");
            throw new PanTradeMQException(e);
        }
        return  send;
    }


    public SendResult sendByTopicEnum(TradeEnum.TopicEnum topic, String keys, String messageText){

        if (StringUtils.isBlank(topic.getTopic())){
            throw  new PanTradeMQException("topic is blank");
        }
        if (StringUtils.isBlank(messageText)){
            throw  new PanTradeMQException("messageText is blank");
        }

        Message message = new Message(topic.getTopic(),topic.getTag(),keys,messageText.getBytes());
        SendResult send ;
        try {
            send = this.producer.send(message);
        } catch (Exception e) {
            logger.error("send message error.....");
            throw new PanTradeMQException(e);
        }
        return  send;
    }



    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }


}
