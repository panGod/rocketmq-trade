package com.pan.trade.goods.service;

import com.pan.bean.TradeGoods;
import com.pan.bean.TradeGoodsNumberLog;
import com.pan.bean.TradeGoodsNumberLogKey;
import com.pan.dao.TradeGoodsMapper;
import com.pan.dao.TradeGoodsNumberLogMapper;
import com.pan.trade.common.exception.TradeOrderException;
import com.pan.trade.common.protocol.goods.*;
import com.pan.trade.common.protocol.voucher.QueryCouponRes;
import com.pan.trade.common.tenum.TradeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Loren on 2018/5/18.
 */
@Service
public class GoodsServiceImpl implements GoodsService {


    @Autowired
    private TradeGoodsMapper tradeGoodsMapper;

    @Autowired
    private TradeGoodsNumberLogMapper tradeGoodsNumberLogMapper;

    /**
     * 查询库存
     * @param queryGoodsReq
     * @return
     */
    public QueryGoodsRes queryGoods(QueryGoodsReq queryGoodsReq) {

        QueryGoodsRes queryGoodsRes = new QueryGoodsRes();
        queryGoodsRes.setCode(TradeEnum.ResultEnum.SUCCESS.getCode());
        queryGoodsRes.setMessage(TradeEnum.ResultEnum.SUCCESS.getMessage());
        try {
            if (queryGoodsReq == null || queryGoodsReq.getGoodsId()==null){
                    throw new Exception("查询商品ID不正确");
            }
            TradeGoods tradeGoods = tradeGoodsMapper.selectByPrimaryKey(queryGoodsReq.getGoodsId());
            if (tradeGoods == null){
                throw new Exception("商品不存在");
            }else {
                BeanUtils.copyProperties(tradeGoods,queryGoodsRes);
            }
        } catch (Exception e) {
            queryGoodsRes.setCode(TradeEnum.ResultEnum.FAIL.getCode());
            queryGoodsRes.setMessage(e.getMessage());
        }



        return null;
    }


    /**
     * 减少库存
     * @param reduceGoodsNumberReq
     * @return
     */
    @Transactional
    public ReduceGoodsNumberRes reduceGoodsNumber(ReduceGoodsNumberReq reduceGoodsNumberReq) {
        ReduceGoodsNumberRes reduceGoodsNumberRes = new ReduceGoodsNumberRes();
        reduceGoodsNumberRes.setCode(TradeEnum.ResultEnum.SUCCESS.getCode());
        reduceGoodsNumberRes.setMessage(TradeEnum.ResultEnum.SUCCESS.getMessage());
        try {
            if (reduceGoodsNumberReq == null || reduceGoodsNumberReq.getGoodsId()==null||reduceGoodsNumberReq.getGoodsNumber()==null ||reduceGoodsNumberReq.getGoodsNumber()<=0){
                throw new Exception("扣减库存请求参数不正确");
            }
            TradeGoods tradeGoods = new TradeGoods();
            tradeGoods.setGoodsId(reduceGoodsNumberReq.getGoodsId());
            tradeGoods.setGoodsNumber(reduceGoodsNumberReq.getGoodsNumber());
            int i = tradeGoodsMapper.reduceGoodsNumber(tradeGoods);
            if (i <= 0){
                throw new TradeOrderException("扣减库存失败");
            }
            TradeGoodsNumberLog tradeGoodsNumberLog = new TradeGoodsNumberLog();
            tradeGoodsNumberLog.setGoodsId(String.valueOf(reduceGoodsNumberReq.getGoodsId()));
            tradeGoodsNumberLog.setOrderId(reduceGoodsNumberReq.getOrderId());
            tradeGoodsNumberLog.setLogTime(new Date());
        } catch (Exception e) {
            reduceGoodsNumberRes.setCode(TradeEnum.ResultEnum.FAIL.getCode());
            reduceGoodsNumberRes.setMessage(e.getMessage());
        }

        return reduceGoodsNumberRes;
    }


    public AddGoodsNumberRes addGoodsNumber(AddGoodsNumberReq addGoodsNumberReq) {
        AddGoodsNumberRes addGoodsNumberRes = new AddGoodsNumberRes();
        addGoodsNumberRes.setCode(TradeEnum.ResultEnum.SUCCESS.getCode());
        addGoodsNumberRes.setMessage(TradeEnum.ResultEnum.SUCCESS.getMessage());
        try {
            if (addGoodsNumberReq == null || addGoodsNumberReq.getGoodsId()==null||addGoodsNumberReq.getGoodsNumber()==null ||addGoodsNumberReq.getGoodsNumber()<=0){
                throw new Exception("增加库存请求参数不正确");
            }
            if (addGoodsNumberReq.getGoodsId()!=null){
                TradeGoodsNumberLogKey tradeGoodsNumberLogKey = new TradeGoodsNumberLogKey();
                tradeGoodsNumberLogKey.setGoodsId(String.valueOf(addGoodsNumberReq.getGoodsId()));
                tradeGoodsNumberLogKey.setOrderId(addGoodsNumberReq.getOrderId());
                TradeGoodsNumberLog tradeGoodsNumberLog = tradeGoodsNumberLogMapper.selectByPrimaryKey(tradeGoodsNumberLogKey);
                if (tradeGoodsNumberLog !=null){
                    throw new Exception("未找到扣减库存记录");
                }
            }
            TradeGoods tradeGoods = new TradeGoods();
            tradeGoods.setGoodsId(addGoodsNumberReq.getGoodsId());
            tradeGoods.setGoodsNumber(addGoodsNumberReq.getGoodsNumber());
            int i = tradeGoodsMapper.addGoodsNumber(tradeGoods);
            if (i<=0){
                throw new Exception("增加库存失败");
            }
        } catch (Exception e) {
            addGoodsNumberRes.setCode(TradeEnum.ResultEnum.FAIL.getCode());
            addGoodsNumberRes.setMessage(e.getMessage());
        }

        return addGoodsNumberRes;
    }


}
