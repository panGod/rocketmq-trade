package com.pan.trade.common.exception;

/**
 * Created by Loren on 2018/5/18.
 */
public class TradeOrderException extends RuntimeException {

    public TradeOrderException() {
        super();
    }

    public TradeOrderException(String message) {
        super(message);
    }

    public TradeOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public TradeOrderException(Throwable cause) {
        super(cause);
    }
}
