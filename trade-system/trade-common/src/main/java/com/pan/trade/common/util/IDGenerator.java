package com.pan.trade.common.util;

import java.util.UUID;

/**
 * Created by Loren on 2018/5/18.
 */
public class IDGenerator {


    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }


}
