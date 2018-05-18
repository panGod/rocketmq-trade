package com.pan.trade.user.api;

import com.pan.trade.common.api.IUserApi;
import com.pan.trade.common.protocol.user.ChangeUserMoneyReq;
import com.pan.trade.common.protocol.user.ChangeUserMoneyRes;
import com.pan.trade.common.protocol.user.QueryUserReq;
import com.pan.trade.common.protocol.user.QueryUserRes;
import com.pan.trade.common.tenum.TradeEnum;
import com.pan.trade.user.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Loren on 2018/5/16.
 */
@Controller
public class UserApiImpl implements IUserApi {

    @Autowired
    private IUserService userService;

    @RequestMapping("/queryUserById")
    @ResponseBody
    public QueryUserRes queryUserById(@RequestBody QueryUserReq queryUserReq) {

        return userService.queryUserById(queryUserReq);
    }

    public ChangeUserMoneyRes changeUserMoney(ChangeUserMoneyReq changeUserMoneyReq) {

        ChangeUserMoneyRes changeUserMoneyRes = new ChangeUserMoneyRes();

        try {
            changeUserMoneyRes = userService.changeUserMoney(changeUserMoneyReq);
        } catch (Exception e) {
            changeUserMoneyRes.setCode(TradeEnum.ResultEnum.FAIL.getCode());
            changeUserMoneyRes.setMessage(TradeEnum.ResultEnum.FAIL.getMessage());
        }

        return changeUserMoneyRes;
    }
}
