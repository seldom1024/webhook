package com.seldom.example;

import com.alibaba.fastjson.JSON;
import com.seldom.exceptions.WebHookApiRequestException;
import com.seldom.exceptions.WebHookApiValidationException;
import com.seldom.methods.BotApi;

import java.awt.*;


/**
 * MyApi
 *
 * @author zhangqi
 * @date 2021/1/16 19:52
 * @since 1.0.0
 */
public class MyApi extends BotApi<List> {
    @Override
    public String getApi() {
        return "http://localhost:8090/test";
    }

    @Override
    public String getBody() {
        Body body = new Body();
        body.setTest("hahha");
        return JSON.toJSONString(body);
    }

    @Override
    public List deserializeResponse(String answer) throws WebHookApiRequestException {
        return null;
    }

    @Override
    public void validate() throws WebHookApiValidationException {

    }
}
