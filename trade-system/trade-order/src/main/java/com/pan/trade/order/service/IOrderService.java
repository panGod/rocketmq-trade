package com.pan.trade.order.service;

import com.pan.trade.common.protocol.order.ConfirmOrderReq;
import com.pan.trade.common.protocol.order.ConfirmOrderRes;

/**
 * Created by Loren on 2018/5/18.
 */
public interface IOrderService {
    ConfirmOrderRes confirmOrder( ConfirmOrderReq confirmOrderReq);

}
