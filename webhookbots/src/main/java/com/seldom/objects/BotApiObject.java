package com.seldom.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * An object from the Bots API received from others Servers
 *
 * @author zhangqi
 * @date 2021/1/16 18:41
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface BotApiObject extends Serializable {
}
