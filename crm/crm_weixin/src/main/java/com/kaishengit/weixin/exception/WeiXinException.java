package com.kaishengit.weixin.exception;

public class WeiXinException extends RuntimeException {

    public WeiXinException(){}

    public WeiXinException(String message) {
        super(message);
    }

    public WeiXinException(Throwable throwable) {
        super(throwable);
    }

    public WeiXinException(String message,Throwable throwable) {
        super(message,throwable);
    }
}
