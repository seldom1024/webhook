package com.seldom.interfaces;

import com.seldom.exceptions.WebHookApiRequestException;
import com.seldom.methods.BotApi;

import java.io.Serializable;

/**
 * Callback to execute api asynchronously
 *
 * @author zhangqi
 * @date 2021/1/16 19:00
 * @since 1.0.0
 */
public interface SentCallback<T extends Serializable> {
    /**
     * Called when the request is successful
     * @param method Method executed
     * @param response Answer from others server
     */
    void onResult(BotApi<T> method, T response);

    /**
     * Called when the request fails
     * @param method Method executed
     * @param apiException Answer from others server (contains error information)
     */
    void onError(BotApi<T> method, WebHookApiRequestException apiException);

    /**
     * Called when the http request throw an exception
     * @param method Method executed
     * @param exception Exception thrown
     */
    void onException(BotApi<T> method, Exception exception);
}
