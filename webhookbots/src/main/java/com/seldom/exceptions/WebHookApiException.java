package com.seldom.exceptions;

/**
 * Base class for any exception from WebHookBots api
 *
 * @author zhangqi
 * @date 2021/1/16 17:59
 * @since 1.0.0
 */
public class WebHookApiException extends Exception {
    public WebHookApiException() {
        super();
    }

    public WebHookApiException(String message) {
        super(message);
    }

    public WebHookApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebHookApiException(Throwable cause) {
        super(cause);
    }

    protected WebHookApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
