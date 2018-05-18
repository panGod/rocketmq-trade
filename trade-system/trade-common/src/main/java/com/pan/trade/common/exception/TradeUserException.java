package com.pan.trade.common.exception;

/**
 * Created by Loren on 2018/5/18.
 */
public class TradeUserException extends RuntimeException {

    public TradeUserException() {
        super();
    }

    public TradeUserException(String message) {
        super(message);
    }

    public TradeUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public TradeUserException(Throwable cause) {
        super(cause);
    }
}
