package com.seldom.example;

import com.seldom.bots.DefaultBotOptions;
import com.seldom.exceptions.WebHookApiException;

/**
 * Main
 *
 * @author zhangqi
 * @date 2021/1/16 19:43
 * @since 1.0.0
 */
public class Main {
    public static void main(String[] args) throws WebHookApiException {
        MySender mySender = new MySender(new DefaultBotOptions());

        mySender.executeAsync(new MyApi());
    }
}
