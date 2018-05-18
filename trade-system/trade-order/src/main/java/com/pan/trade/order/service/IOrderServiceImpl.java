package com.pan.trade.order.service;

import com.alibaba.fastjson.JSON;
import com.pan.bean.TradeOrder;
import com.pan.dao.TradeOrderMapper;
import com.pan.trade.common.api.ICouponApi;
import com.pan.trade.common.api.IGoodsApi;
import com.pan.trade.common.api.IUserApi;
import com.pan.trade.common.exception.TradeOrderException;
import com.pan.trade.common.protocol.goods.QueryGoodsReq;
import com.pan.trade.common.protocol.goods.QueryGoodsRes;
import com.pan.trade.common.protocol.goods.ReduceGoodsNumberReq;
import com.pan.trade.common.protocol.goods.ReduceGoodsNumberRes;
import com.pan.trade.common.protocol.mq.CancelOrderMQ;
import com.pan.trade.common.protocol.order.ConfirmOrderReq;
import com.pan.trade.common.protocol.order.ConfirmOrderRes;
import com.pan.trade.common.protocol.user.ChangeUserMoneyReq;
import com.pan.trade.common.protocol.user.ChangeUserMoneyRes;
import com.pan.trade.common.protocol.user.QueryUserReq;
import com.pan.trade.common.protocol.user.QueryUserRes;
import com.pan.trade.common.protocol.voucher.ChangeCouponStatusReq;
import com.pan.trade.common.protocol.voucher.ChangeCouponStatusRes;
import com.pan.trade.common.protocol.voucher.QueryCouponReq;
import com.pan.trade.common.protocol.voucher.QueryCouponRes;
import com.pan.trade.common.rocketmq.PanProducer;
import com.pan.trade.common.tenum.TradeEnum;
import com.pan.trade.common.util.IDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by Loren on 2018/5/18.
 */
@Service
public class IOrderServiceImpl implements IOrderService{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IGoodsApi goodsApi;

    @Autowired
    private TradeOrderMapper tradeOrderMapper;

    @Autowired
    private ICouponApi  couponApi;

    @Autowired
    private IUserApi userApi;

    @Autowired
    private PanProducer panProducer;


    public ConfirmOrderRes confirmOrder(ConfirmOrderReq confirmOrderReq) {

        ConfirmOrderRes confirmOrderRes = new ConfirmOrderRes();
        confirmOrderRes.setCode(TradeEnum.ResultEnum.SUCCESS.getCode());
        try {
            QueryGoodsReq queryGoodsReq = new QueryGoodsReq();
            queryGoodsReq.setGoodsId(confirmOrderReq.getGoodsId());
            QueryGoodsRes queryGoodsRes = goodsApi.queryGoods(queryGoodsReq);
            //检查校验
            checkConfirmOrderReq(confirmOrderReq,queryGoodsRes);
            //创建不可见订单
            String orderID = createNoConfirmOrder(confirmOrderReq);
            //调用远程服务
            callRemoteService(confirmOrderReq,orderID);

        } catch (Exception e) {
            confirmOrderRes.setCode(TradeEnum.ResultEnum.FAIL.getCode());
            confirmOrderRes.setMessage(e.getMessage());
        }
        //调用远程服务、扣优惠券 扣库存  扣余额 如果调用成功 更改订单状态为可见  失败则发送mq消息，进行取消订单


        return null;
    }

    /**
     * 调用远程服务
     * @param confirmOrderReq
     * @param orderID
     */
    private void callRemoteService(ConfirmOrderReq confirmOrderReq, String orderID) {

        try {
            //调用优惠券
            if (StringUtils.isNoneBlank(confirmOrderReq.getCouponId())){
                ChangeCouponStatusReq changeCouponStatusReq = new ChangeCouponStatusReq();
                changeCouponStatusReq.setCouponId(confirmOrderReq.getCouponId());
                changeCouponStatusReq.setIsUsed(TradeEnum.CouponYESORNOStatusEnum.YES.getCode());
                changeCouponStatusReq.setOrderId(orderID);
                ChangeCouponStatusRes changeCouponStatusRes = couponApi.changeCouponStatus(changeCouponStatusReq);
                if (changeCouponStatusRes.getCode().equals(TradeEnum.ResultEnum.SUCCESS)){
                    throw new Exception("优惠券使用失败！");
                }
            }
            //扣余额
            if (confirmOrderReq.getMoneyPaid()!=null && confirmOrderReq.getMoneyPaid().compareTo(BigDecimal.ZERO) == 1){
                ChangeUserMoneyReq changeUserMoneyReq = new ChangeUserMoneyReq();
                changeUserMoneyReq.setUserId(confirmOrderReq.getUserId());
                changeUserMoneyReq.setOrderId(orderID);
                changeUserMoneyReq.setMoneyLogType(Integer.valueOf(TradeEnum.UserMoneyLogTypeStatusEnum.PAID.getCode()));
                ChangeUserMoneyRes changeUserMoneyRes = userApi.changeUserMoney(changeUserMoneyReq);
                if (!changeUserMoneyRes.getCode().equals(TradeEnum.ResultEnum.SUCCESS.getCode())){
                    throw new Exception("扣除用户余额失败!");
                }
            }
            //扣库存
            ReduceGoodsNumberReq reduceGoodsNumberReq = new ReduceGoodsNumberReq();
            reduceGoodsNumberReq.setOrderId(orderID);
            reduceGoodsNumberReq.setGoodsNumber(confirmOrderReq.getGoodsNumer());
            reduceGoodsNumberReq.setGoodsId(confirmOrderReq.getGoodsId());
            ReduceGoodsNumberRes reduceGoodsNumberRes = goodsApi.reduceGoodsNumber(reduceGoodsNumberReq);
            if (!reduceGoodsNumberRes.getCode().equals(TradeEnum.ResultEnum.SUCCESS.getCode())){
                throw new Exception("扣减库存失败！");
            }
            //更改订单状态
            TradeOrder tradeOrder = new TradeOrder();
            tradeOrder.setOrderId(orderID);
            tradeOrder.setOrderStatus(TradeEnum.OrderStatusEnum.CONFIRM.getCode());
            tradeOrder.setConfirmTime(new Date());
            int updateByPrimaryKeySelective = tradeOrderMapper.updateByPrimaryKeySelective(tradeOrder);
            if (updateByPrimaryKeySelective <= 0){
                throw  new Exception("更新订单状态失败");
            }
        }catch (Exception e){
            //发送mq消息
            CancelOrderMQ cancelOrderMQ = new CancelOrderMQ();
            cancelOrderMQ.setOrderId(orderID);
            cancelOrderMQ.setUserId(confirmOrderReq.getUserId());
            cancelOrderMQ.setGoodsNumber(confirmOrderReq.getGoodsNumer());
            cancelOrderMQ.setGoodsId(confirmOrderReq.getGoodsId());
            cancelOrderMQ.setCouponId(cancelOrderMQ.getCouponId());
            try {
                SendResult sendResult = panProducer.sendByTopicEnum(TradeEnum.TopicEnum.ORDER_CANCEL, orderID, JSON.toJSONString(cancelOrderMQ));
                System.out.println(sendResult);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }

    /**
     * 创建不可见的订单
     * @param confirmOrderReq
     * @throws Exception
     */
    private String createNoConfirmOrder(ConfirmOrderReq confirmOrderReq) throws Exception {
        TradeOrder tradeOrder = new TradeOrder();
        String id = IDGenerator.generateUUID();
        tradeOrder.setOrderId(id);
        tradeOrder.setUserId(confirmOrderReq.getUserId());
        tradeOrder.setOrderStatus(TradeEnum.OrderStatusEnum.NO_CONFIRM.getCode());
        tradeOrder.setPayStatus(TradeEnum.PayStatusEnum.NO_PAY.getCode());
        tradeOrder.setShippingStatus(TradeEnum.ShippingStatusEnum.NO_SHIP.getCode());
        tradeOrder.setAddress(confirmOrderReq.getAddress());
        tradeOrder.setConfirmer(confirmOrderReq.getComsignee());
        tradeOrder.setGoodsId(confirmOrderReq.getGoodsId());
        tradeOrder.setGoodsNum(confirmOrderReq.getGoodsNumer());
        tradeOrder.setGoodsPrice(confirmOrderReq.getGoodsPrice());
        BigDecimal amount = confirmOrderReq.getGoodsPrice().multiply(new BigDecimal(confirmOrderReq.getGoodsNumer()));
        tradeOrder.setGoodsAmount(amount);
        BigDecimal shippingFee = calculateShippingFee(amount);
        if (confirmOrderReq.getShippingFee().compareTo(shippingFee)!=0){
            throw new Exception("快递费用不正确!");
        }
        tradeOrder.setShippingFee(shippingFee);
        BigDecimal orderAmount = amount.add(shippingFee);
        if (orderAmount.compareTo(confirmOrderReq.getOrderAmount()) != 0){
            throw  new Exception("订单总价异常，请重新下单!");
        }
        tradeOrder.setOrderAmount(orderAmount);
        String couponId = confirmOrderReq.getCouponId();
        if (StringUtils.isNoneBlank(couponId)){
            //查询优惠券是否有效
            QueryCouponReq couponReq = new QueryCouponReq();
            couponReq.setCouponId(couponId);
            QueryCouponRes queryCouponRes = couponApi.queryCoupon(couponReq);
            if (queryCouponRes==null || !queryCouponRes.getCode().equals(TradeEnum.ResultEnum.SUCCESS.getCode())){
                throw new Exception("优惠券非法");
            }
            if (queryCouponRes.getIsUsed().equals(TradeEnum.CouponYESORNOStatusEnum.NO.getCode())){
                throw new Exception("优惠券已使用");
            }
            tradeOrder.setCouponId(couponId);
            tradeOrder.setCouponPrice(queryCouponRes.getCouponPrice());
        }else {
            tradeOrder.setCouponPrice(BigDecimal.ZERO);
        }
        //余额支付
        if (confirmOrderReq.getMoneyPaid() != null){
            int i = confirmOrderReq.getMoneyPaid().compareTo(BigDecimal.ZERO);
            if(i==-1){
                throw new Exception("余额金额非法");
            }
            if (i==1){
                //判断当前用户余额是否足够
                QueryUserReq queryUserReq = new QueryUserReq();
                queryUserReq.setUserId(confirmOrderReq.getUserId());
                QueryUserRes queryUserRes = userApi.queryUserById(queryUserReq);
                if (queryUserRes==null||!queryUserRes.getCode().equals(TradeEnum.ResultEnum.SUCCESS.getCode())){
                    throw  new Exception("用户非法");
                }
                if (queryUserRes.getUserMoney().compareTo(confirmOrderReq.getMoneyPaid()) == -1){
                    throw  new Exception("用户余额不足");
                }
            }
        }else {
            //使用其他支付
            tradeOrder.setMoneyPaid(BigDecimal.ZERO);
        }
        BigDecimal payAmount = orderAmount.subtract(tradeOrder.getMoneyPaid()).subtract(tradeOrder.getMoneyPaid());
        tradeOrder.setPayAmount(payAmount);
        tradeOrder.setCreateTime(new Date());
        int insert = tradeOrderMapper.insert(tradeOrder);
        if (insert != 1){
            throw  new Exception("保存订单失败");
        }
        return id;
    }

    private BigDecimal calculateShippingFee(BigDecimal goodsAmount){
        if (goodsAmount.doubleValue() >100.0){
            return  BigDecimal.ZERO;
        }else {
            return new BigDecimal("10");
        }
    }

    private void checkConfirmOrderReq(ConfirmOrderReq confirmOrderReq, QueryGoodsRes queryGoodsRes) {

        if (confirmOrderReq == null){
            throw new TradeOrderException("下单信息不能为空！");
        }

        if (confirmOrderReq.getUserId() == null){
            throw new TradeOrderException("会员账号不能为空!");
        }


        if (confirmOrderReq.getGoodsId() == null){
            throw new TradeOrderException("商品编号不能为空!");
        }


        if (confirmOrderReq.getGoodsNumer() == null ||confirmOrderReq.getGoodsNumer()<=0){
            throw new TradeOrderException("购买数量不能小于0!");
        }

        if (confirmOrderReq.getAddress() == null){
            throw new TradeOrderException("购买地址不能为空!");
        }

        if (queryGoodsRes == null || queryGoodsRes.getCode().equals(TradeEnum.ResultEnum.FAIL.getCode())){
            throw new TradeOrderException("未查询到该商品["+confirmOrderReq.getGoodsId()+"]");
        }

        if (queryGoodsRes.getGoodsNumber() < confirmOrderReq.getGoodsNumer()){
            throw  new TradeOrderException("商品库存不足");
        }

        if (queryGoodsRes.getGoodsPrice().compareTo(confirmOrderReq.getGoodsPrice()) != 0){
            throw new TradeOrderException("商品价格有变化，请重新下单");
        }

        if (confirmOrderReq.getShippingFee() == null){
            confirmOrderReq.setShippingFee(BigDecimal.ZERO);
        }
        if (confirmOrderReq.getOrderAmount() == null){
            confirmOrderReq.setOrderAmount(BigDecimal.ZERO);
        }


    }
}
