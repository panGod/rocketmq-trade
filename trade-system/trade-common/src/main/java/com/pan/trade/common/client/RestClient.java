package com.pan.trade.common.client;

import com.pan.trade.common.protocol.user.QueryUserReq;
import com.pan.trade.common.protocol.user.QueryUserRes;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Loren on 2018/5/18.
 */
public class RestClient {

    private  static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        QueryUserReq queryUserReq  = new QueryUserReq();
        queryUserReq.setUserId(1);
        QueryUserRes queryUserRes = restTemplate.postForObject("http://localhost:8080/user/queryUserById", queryUserReq, QueryUserRes.class);
        System.out.println(queryUserRes);
    }


}
