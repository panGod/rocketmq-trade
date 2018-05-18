package com.pan.trade.common.api;

import com.pan.trade.common.protocol.order.ConfirmOrderReq;
import com.pan.trade.common.protocol.order.ConfirmOrderRes;

/**
 * Created by Loren on 2018/5/16.
 */
public interface IOrderApi {


    ConfirmOrderRes confirmOrder(ConfirmOrderReq confirmOrderReq);


}
