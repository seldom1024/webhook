package com.seldom.facilities;

import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

/**
 * HttpSSLConnectionSocketFactory
 *
 * @author zhangqi
 * @date 2021/1/16 19:31
 * @since 1.0.0
 */
public class HttpSSLConnectionSocketFactory extends SSLConnectionSocketFactory {

    public HttpSSLConnectionSocketFactory(final SSLContext sslContext) {
        super(sslContext, new NoopHostnameVerifier());
    }

    @Override
    public Socket createSocket(final HttpContext context) throws IOException {
        InetSocketAddress socketAddress = (InetSocketAddress) context.getAttribute("socketAddress");
        Proxy proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
        return new Socket(proxy);
    }

    @Override
    public Socket connectSocket(
            int connectTimeout,
            Socket socket,
            HttpHost host,
            InetSocketAddress remoteAddress,
            InetSocketAddress localAddress,
            HttpContext context) throws IOException {
        String hostName = host.getHostName();
        int port = remoteAddress.getPort();
        InetSocketAddress unresolvedRemote = InetSocketAddress.createUnresolved(hostName, port);
        return super.connectSocket(connectTimeout, socket, host, unresolvedRemote, localAddress, context);
    }
}
