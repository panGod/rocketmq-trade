package com.pan.trade.user.service;

import com.pan.trade.common.protocol.user.ChangeUserMoneyReq;
import com.pan.trade.common.protocol.user.ChangeUserMoneyRes;
import com.pan.trade.common.protocol.user.QueryUserReq;
import com.pan.trade.common.protocol.user.QueryUserRes;

/**
 * Created by Loren on 2018/5/16.
 */
public interface IUserService {

    QueryUserRes queryUserById(QueryUserReq queryUserReq);
    ChangeUserMoneyRes changeUserMoney(ChangeUserMoneyReq changeUserMoneyReq);
}
