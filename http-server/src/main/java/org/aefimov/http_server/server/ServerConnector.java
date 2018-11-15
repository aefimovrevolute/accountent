package org.aefimov.http_server.server;

import io.netty.channel.ChannelInitializer;

public interface ServerConnector {

    int getPort();

    ChannelInitializer<?> getChannelInitializer();

}
