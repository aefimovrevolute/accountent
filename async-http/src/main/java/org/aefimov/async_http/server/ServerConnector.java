package org.aefimov.async_http.server;

import io.netty.channel.ChannelInitializer;

public interface ServerConnector {

    int getPort();

    ChannelInitializer<?> getChannelInitializer();

}
