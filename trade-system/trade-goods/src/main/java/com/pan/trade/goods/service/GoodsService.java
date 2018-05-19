package com.pan.trade.goods.service;

import com.pan.trade.common.protocol.goods.*;

/**
 * Created by Loren on 2018/5/18.
 */
public interface GoodsService {

     QueryGoodsRes queryGoods(QueryGoodsReq queryGoodsReq);

     ReduceGoodsNumberRes reduceGoodsNumber(ReduceGoodsNumberReq reduceGoodsNumberReq);

     AddGoodsNumberRes addGoodsNumber(AddGoodsNumberReq addGoodsNumberReq);
}
