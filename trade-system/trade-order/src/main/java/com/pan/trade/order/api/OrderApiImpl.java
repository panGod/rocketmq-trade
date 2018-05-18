package com.pan.trade.order.api;

import com.pan.trade.common.api.IOrderApi;
import com.pan.trade.common.protocol.order.ConfirmOrderReq;
import com.pan.trade.common.protocol.order.ConfirmOrderRes;
import com.pan.trade.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Loren on 2018/5/18.
 */
@Controller
public class OrderApiImpl implements IOrderApi {


    @Autowired
    private IOrderService orderService;

    @RequestMapping(value = "/confirmOrder",method = RequestMethod.POST)
    @ResponseBody
    public ConfirmOrderRes confirmOrder(@RequestBody ConfirmOrderReq confirmOrderReq) {
        ConfirmOrderRes confirmOrderRes = orderService.confirmOrder(confirmOrderReq);
        return confirmOrderRes;
    }
}
