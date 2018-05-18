package com.pan.trade.common.exception;

/**
 * Created by Loren on 2018/5/16.
 */
public class PanTradeMQException extends RuntimeException {


    public PanTradeMQException() {
        super();
    }

    public PanTradeMQException(String message) {
        super(message);
    }

    public PanTradeMQException(String message, Throwable cause) {
        super(message, cause);
    }

    public PanTradeMQException(Throwable cause) {
        super(cause);
    }


}
