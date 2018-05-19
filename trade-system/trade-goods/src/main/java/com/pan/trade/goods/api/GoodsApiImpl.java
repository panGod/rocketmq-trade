package com.pan.trade.goods.api;

import com.pan.trade.common.api.IGoodsApi;
import com.pan.trade.common.protocol.goods.*;
import com.pan.trade.common.tenum.TradeEnum;
import com.pan.trade.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Loren on 2018/5/18.
 */

@Controller
public class GoodsApiImpl implements IGoodsApi {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/queryGoods")
    @ResponseBody
    public QueryGoodsRes queryGoods(QueryGoodsReq queryGoodsReq) {
        return goodsService.queryGoods(queryGoodsReq);
    }

    @RequestMapping("/reduceGoodsNumber")
    @ResponseBody
    public ReduceGoodsNumberRes reduceGoodsNumber(ReduceGoodsNumberReq reduceGoodsNumberReq) {

        ReduceGoodsNumberRes reduceGoodsNumberRes = new ReduceGoodsNumberRes();

        try {
            reduceGoodsNumberRes = goodsService.reduceGoodsNumber(reduceGoodsNumberReq);
        } catch (Exception e) {
            reduceGoodsNumberRes.setCode(TradeEnum.ResultEnum.FAIL.getCode());
            reduceGoodsNumberRes.setMessage(e.getMessage());
        }
        return reduceGoodsNumberRes;
    }



    @RequestMapping("/addGoodsNumber")
    @ResponseBody
    public AddGoodsNumberRes addGoodsNumber(AddGoodsNumberReq addGoodsNumberReq){
        return goodsService.addGoodsNumber(addGoodsNumberReq);
    }
}
