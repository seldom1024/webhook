package com.seldom.example;

/**
 * Body
 *
 * @author zhangqi
 * @date 2021/1/16 19:59
 * @since 1.0.0
 */
public class Body {

    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "Body{" +
                "test='" + test + '\'' +
                '}';
    }
}
