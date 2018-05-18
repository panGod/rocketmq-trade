package com.pan.trade.common.api;

import com.pan.trade.common.protocol.goods.QueryGoodsReq;
import com.pan.trade.common.protocol.goods.QueryGoodsRes;
import com.pan.trade.common.protocol.goods.ReduceGoodsNumberReq;
import com.pan.trade.common.protocol.goods.ReduceGoodsNumberRes;

/**
 * Created by Loren on 2018/5/16.
 */
public interface IGoodsApi {

    QueryGoodsRes queryGoods(QueryGoodsReq queryGoodsReq);

    ReduceGoodsNumberRes reduceGoodsNumber(ReduceGoodsNumberReq reduceGoodsNumberReq);


}
