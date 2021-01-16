package com.seldom.bots;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;

import java.util.List;

/**
 * DefaultBotOptions
 *
 * @author zhangqi
 * @date 2021/1/16 19:21
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
public class DefaultBotOptions {

    ///< Max number of threads used for async methods executions (default 1)
    private int maxThreads;
    private RequestConfig requestConfig;
    private volatile HttpContext httpContext;
    private ExponentialBackOff exponentialBackOff;
    private Integer maxWebHookConnections;
    private String baseUrl;
    private List<String> allowedUpdates;
    private ProxyType proxyType;
    private String proxyHost;
    private int proxyPort;
    private int getUpdatesTimeout;
    private int getUpdatesLimit;

    public DefaultBotOptions() {
        maxThreads = 1;
        httpContext = HttpClientContext.create();
        proxyType = ProxyType.NO_PROXY;
        getUpdatesLimit = ApiConstants.GET_UPDATES_TIMEOUT;
        getUpdatesLimit = 100;
    }


    public enum ProxyType {
        /**
         * NO_PROXY
         */
        NO_PROXY,

        /**
         * HTTP
         */
        HTTP,

        /**
         * SOCKS4
         */
        SOCKS4,

        /**
         * SOCKS5
         */
        SOCKS5
    }
}
