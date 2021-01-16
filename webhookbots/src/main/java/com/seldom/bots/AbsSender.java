package com.seldom.bots;

import com.seldom.exceptions.WebHookApiException;
import com.seldom.interfaces.SentCallback;
import com.seldom.methods.BotApi;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

/**
 * AbsSender
 *
 * @author zhangqi
 * @date 2021/1/16 17:47
 * @since 1.0.0
 */
public abstract class AbsSender {

    protected AbsSender() {
    }

    public <T extends Serializable, Method extends BotApi<T>, Callback extends SentCallback<T>> void executeAsync(Method method, Callback callback) throws WebHookApiException {
        if (method == null) {
            throw new WebHookApiException("Parameter method can not be null");
        }
        if (callback == null) {
            throw new WebHookApiException("Parameter callback can not be null");
        }
        sendApiMethodAsync(method, callback);
    }

    public <T extends Serializable, Method extends BotApi<T>> CompletableFuture<T> executeAsync(Method method) throws WebHookApiException {
        if (method == null) {
            throw new WebHookApiException("Parameter method can not be null");
        }
        return sendApiMethodAsync(method);
    }

    public <T extends Serializable, Method extends BotApi<T>> T execute(Method method) throws WebHookApiException {
        if (method == null) {
            throw new WebHookApiException("Parameter method can not be null");
        }
        return sendApiMethod(method);
    }

    /**
     * sendApiMethod
     *
     * @param method
     * @param <T>
     * @param <Method>
     * @return
     */
    protected abstract <T extends Serializable, Method extends BotApi<T>> T sendApiMethod(Method method) throws WebHookApiException;

    /**
     * sendApiMethodAsync
     *
     * @param method
     * @param <T>
     * @param <Method>
     * @return
     */
    protected abstract <T extends Serializable, Method extends BotApi<T>> CompletableFuture<T> sendApiMethodAsync(Method method);

    /**
     * sendApiMethodAsync
     *
     * @param method
     * @param callback
     * @param <T>
     * @param <Method>
     * @param <Callback>
     */
    protected abstract <T extends Serializable, Method extends BotApi<T>, Callback extends SentCallback<T>> void sendApiMethodAsync(Method method, Callback callback);

}
