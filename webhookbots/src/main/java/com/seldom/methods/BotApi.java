package com.seldom.methods;
import java.io.Serializable;

/**
 * BotApiMethod
 *
 * @author zhangqi
 * @date 2021/1/16 18:33
 * @since 1.0.0
 */
public abstract class BotApi<T extends Serializable> extends PartialBotApi<T>{
    /**
     * Getter for api
     * @return Method path
     */
    public abstract String getApi();

    /**
     * Getter for post body
     * @return
     */
    public abstract String getBody();
}
