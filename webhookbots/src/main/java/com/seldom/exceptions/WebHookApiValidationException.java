package com.seldom.exceptions;

import com.seldom.methods.PartialBotApi;
import com.seldom.objects.BotApiObject;

/**
 * WebHookApiValidationException
 *
 * @author zhangqi
 * @date 2021/1/16 18:55
 * @since 1.0.0
 */
public class WebHookApiValidationException extends WebHookApiException {

    private PartialBotApi<?> api;
    private BotApiObject object;

    public WebHookApiValidationException(String message, PartialBotApi<?> api) {
        super(message);
        this.api = api;
    }

    public WebHookApiValidationException(String message, BotApiObject object) {
        super(message);
        this.object = object;
    }

    public BotApiObject getObject() {
        return object;
    }

    public PartialBotApi<?> getMethod() {
        return api;
    }

    @Override
    public String toString() {
        if (api != null) {
            return super.toString() + " in api: " + api.toString();
        }
        if (object != null) {
            return super.toString() + " in object: " + object.toString();
        }
        return super.toString();
    }
}
