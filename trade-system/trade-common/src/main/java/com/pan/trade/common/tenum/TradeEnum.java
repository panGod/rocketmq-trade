package com.pan.trade.common.tenum;

/**
 * Created by Loren on 2018/5/16.
 */
public class TradeEnum {

    /**
     * 调用服务
     */
    public enum RestServerEnum{

        ORDER("http://localhost:8080/","order"),
        GOODS("http://localhost:8081/","goods"),
        PAY("http://localhost:8082/","pay"),
        USER("http://localhost:8083/","user"),
        VOUCHER("http://localhost:8084/","voucher");
        private String  url;
        private String type;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        RestServerEnum(String url, String type) {
            this.url = url;
            this.type = type;
        }
    }


    /**
     * 结果状态
     */
    public enum ResultEnum{

        SUCCESS("200","成功"),FAIL("500","失败");

        private String code;
        private String message;

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        ResultEnum(String code, String message) {
            this.code = code;
            this.message = message;
        }

    }

    /**
     * 订单状态
     */
    public enum OrderStatusEnum{

        NO_CONFIRM("0","未确认"),CONFIRM("1","已确认"),CANCEL("1","已取消"),INVALID("3","无效"),RETURNED("4","退货");

        private String statusCode;
        private String message;

        public String getCode() {
            return statusCode;
        }

        public String getMessage() {
            return message;
        }

        OrderStatusEnum(String statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message;
        }

    }

    /**
     * 支付状态
     */
    public enum PayStatusEnum{

        NO_PAY("0","未付款"),PAYING("1","已付款"),PAID("2","已付款");

        private String statusCode;
        private String message;

        public String getCode() {
            return statusCode;
        }

        public String getMessage() {
            return message;
        }

        PayStatusEnum(String statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message;
        }

    }

    /**
     * 发货状态
     */
    public enum ShippingStatusEnum{

        NO_SHIP("0","未发货"),SHIPPED("1","已发货"),RECIVED("2","已收货");

        private String statusCode;
        private String message;

        public String getCode() {
            return statusCode;
        }

        public String getMessage() {
            return message;
        }

        ShippingStatusEnum(String statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message;
        }

    }

    /**
     * 优惠券
     */
    public enum CouponYESORNOStatusEnum{

        NO("0","否"),YES("1","是");

        private String statusCode;
        private String message;

        public String getCode() {
            return statusCode;
        }

        public String getMessage() {
            return message;
        }

        CouponYESORNOStatusEnum(String statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message;
        }

    }


    public enum UserMoneyLogTypeStatusEnum{

        PAID("1","订单付款"),RETURN("2","订单退款");

        private String statusCode;
        private String message;

        public String getCode() {
            return statusCode;
        }

        public String getMessage() {
            return message;
        }

        UserMoneyLogTypeStatusEnum(String statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message;
        }

    }

    /**
     * mqEnum
     */
    public enum TopicEnum{

        ORDER_CONFIRM("orderTopic","confirm"),ORDER_CANCEL("orderTopic","cancel"),PAY_PAID("payTopic","paid");

        private String topic;
        private String tag;


        public String getTopic() {
            return topic;
        }

        public String getTag() {
            return tag;
        }

        TopicEnum(String topic, String tag) {
            this.topic = topic;
            this.tag = tag;
        }
    }





}
