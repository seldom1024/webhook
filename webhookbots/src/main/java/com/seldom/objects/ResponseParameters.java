package com.seldom.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * ResponseParameters
 *
 * @author zhangqi
 * @date 2021/1/16 18:42
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseParameters implements BotApiObject {
    private static final String MESSAGE = "message";
    private static final String CODE = "code";

    /**
     * response from others server
     */
    @JsonProperty(MESSAGE)
    private Object message;

    /**
     * response from others server
     */
    @JsonProperty(CODE)
    private String code;
}
