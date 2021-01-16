package com.seldom.bots;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seldom.exceptions.WebHookApiException;
import com.seldom.exceptions.WebHookApiRequestException;
import com.seldom.exceptions.WebHookApiValidationException;
import com.seldom.facilities.WebHookHttpClientBuilder;
import com.seldom.interfaces.SentCallback;
import com.seldom.methods.BotApi;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Implementation of all the methods
 *
 * @author zhangqi
 * @date 2021/1/16 19:11
 * @since 1.0.0
 */
public abstract class DefaultAbsSender extends AbsSender{

    protected final ExecutorService exe;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DefaultBotOptions options;
    private final CloseableHttpClient httpClient;
    private final RequestConfig requestConfig;

    protected DefaultAbsSender(DefaultBotOptions options) {
        super();

        this.exe = Executors.newFixedThreadPool(options.getMaxThreads());
        this.options = options;

        httpClient = WebHookHttpClientBuilder.build(options);
        configureHttpContext();

        final RequestConfig configFromOptions = options.getRequestConfig();
        if (configFromOptions != null) {
            this.requestConfig = configFromOptions;
        } else {
            this.requestConfig = RequestConfig.copy(RequestConfig.custom().build())
                    .setSocketTimeout(ApiConstants.SOCKET_TIMEOUT)
                    .setConnectTimeout(ApiConstants.SOCKET_TIMEOUT)
                    .setConnectionRequestTimeout(ApiConstants.SOCKET_TIMEOUT).build();
        }
    }

    // Private methods

    private void configureHttpContext() {

        if (options.getProxyType() != DefaultBotOptions.ProxyType.NO_PROXY) {
            InetSocketAddress socksAddr = new InetSocketAddress(options.getProxyHost(), options.getProxyPort());
            options.getHttpContext().setAttribute("socketAddress", socksAddr);
        }

        if (options.getProxyType() == DefaultBotOptions.ProxyType.SOCKS4) {
            options.getHttpContext().setAttribute("socksVersion", 4);
        }
        if (options.getProxyType() == DefaultBotOptions.ProxyType.SOCKS5) {
            options.getHttpContext().setAttribute("socksVersion", 5);
        }

    }

    private <T extends Serializable, Method extends BotApi<T>> String sendMethodRequest(Method method) throws WebHookApiValidationException, IOException {
        method.validate();
        String url = method.getApi();
        HttpPost httppost = configuredHttpPost(url);
        httppost.addHeader("charset", StandardCharsets.UTF_8.name());
        httppost.setEntity(new StringEntity(method.getBody(), ContentType.APPLICATION_JSON));
        return sendHttpPostRequest(httppost);
    }

    private HttpPost configuredHttpPost(String url) {
        HttpPost httppost = new HttpPost(url);
        httppost.setConfig(requestConfig);
        return httppost;
    }

    private String sendHttpPostRequest(HttpPost httppost) throws IOException {
        try (CloseableHttpResponse response = httpClient.execute(httppost, options.getHttpContext())) {
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        }
    }


    @Override
    protected <T extends Serializable, Method extends BotApi<T>> T sendApiMethod(Method method)  throws WebHookApiException {
        try {
            String responseContent = sendMethodRequest(method);
            return method.deserializeResponse(responseContent);
        } catch (IOException e) {
            throw new WebHookApiException("Unable to execute " + method.getApi() + " url", e);
        }
    }

    @Override
    protected <T extends Serializable, Method extends BotApi<T>> CompletableFuture<T> sendApiMethodAsync(Method method) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        exe.submit(() -> {
            try {
                String responseContent = sendMethodRequest(method);
                completableFuture.complete(method.deserializeResponse(responseContent));
            } catch (IOException | WebHookApiValidationException | WebHookApiRequestException e) {
                completableFuture.completeExceptionally(e);
            }
        });
        return completableFuture;
    }

    @Override
    protected <T extends Serializable, Method extends BotApi<T>, Callback extends SentCallback<T>> void sendApiMethodAsync(Method method, Callback callback) {
        //noinspection Convert2Lambda
        exe.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    String responseContent = sendMethodRequest(method);
                    try {
                        callback.onResult(method, method.deserializeResponse(responseContent));
                    } catch (WebHookApiRequestException e) {
                        callback.onError(method, e);
                    }
                } catch (IOException | WebHookApiValidationException e) {
                    callback.onException(method, e);
                }

            }
        });
    }
}
