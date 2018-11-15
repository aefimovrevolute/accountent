package org.aefimov.http_server.server;

import com.google.common.collect.Lists;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BasicNettyServer implements Server {

    private static final Logger logger = LoggerFactory.getLogger(BasicNettyServer.class);

    private final List<ServerConnector> connectors;
    private List<ChannelFuture> channels = Lists.newArrayList();
    private final EventLoopGroup masterGroup;
    private final EventLoopGroup slaveGroup;

    private boolean shutdownCalled = false;

    public BasicNettyServer(final ServerConnector connector) {
        this(Lists.newArrayList(connector));
        System.out.println(String.format("The port for binding is [%s].", connector.getPort()));
    }

    public BasicNettyServer(final List<ServerConnector> connectors) {
        this.connectors = connectors;
        masterGroup = new NioEventLoopGroup();
        slaveGroup = new NioEventLoopGroup();
    }

    protected String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                shutdown();
            }
        });

        System.out.println(String.format("Starting new server of type \"%s\"...", getName()));
        logger.info(String.format("Starting new server of type \"%s\"...", getName()));

        try {
            // for each accountant, build a bootstrap, start and save the ChannelFuture
            for (final ServerConnector connector : connectors) {
                final ServerBootstrap bootstrap =
                        new ServerBootstrap()
                                .group(masterGroup, slaveGroup)
                                .channel(NioServerSocketChannel.class)
                                .handler(new LoggingHandler(LogLevel.INFO))
                                .childHandler(connector.getChannelInitializer())
                                .option(ChannelOption.SO_BACKLOG, 128)
                                .childOption(ChannelOption.SO_KEEPALIVE, true);
                channels.add(bootstrap.bind(connector.getPort()).sync());
            }
        } catch (final InterruptedException e) {
        }

        logger.info("Startup complete.");
        System.out.println("Startup complete.");
    }

    @Override
    public void shutdown() {
        if (!shutdownCalled) {
            logger.info(String.format("Shutting down server of type \"%s\"...", getName()));
        }

        slaveGroup.shutdownGracefully();
        masterGroup.shutdownGracefully();

        for (final ChannelFuture channel : channels) {
            try {
                channel.channel().closeFuture().sync();
            } catch (InterruptedException e) {
            }
        }

        if (!shutdownCalled) {
            shutdownCalled = true;
            logger.info("Shutdown complete.");
        }
    }
}
