package com.seldom.methods;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seldom.exceptions.WebHookApiRequestException;
import com.seldom.interfaces.Validable;

import java.io.Serializable;

/**
 * PartialBotApi
 *
 * @author zhangqi
 * @date 2021/1/16 18:35
 * @since 1.0.0
 */
public abstract class PartialBotApi<T extends Serializable> implements Validable {

    @JsonIgnore
    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Deserialize a json answer to the response type to a method
     * @param answer Json answer received
     * @return Answer for the method
     */
    public abstract T deserializeResponse(String answer) throws WebHookApiRequestException;
}
