package org.aefimov.http_server.server.http;

import com.google.inject.Injector;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.aefimov.http_server.server.ServerConnector;

import java.util.Optional;

public class HttpServerConnector implements ServerConnector {

    private final int port;
    private final Optional<Injector> injector;

    public static final String CODEC_HANDLER_NAME = "codec_handler";
    public static final String COMPRESSOR_HANDLER_NAME = "compressor_handler";
    public static final String AGGREGATOR_HANDLER_NAME = "aggregator_handler";
    public static final String HTTP_REQUEST_HANDLER_NAME = "request_handler";

    public HttpServerConnector(int port, Optional<Injector> injector) {
        this.port = port;
        this.injector = injector;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public ChannelInitializer<?> getChannelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(final SocketChannel ch) throws Exception {
                ch.pipeline().addLast(CODEC_HANDLER_NAME, new HttpServerCodec());
                ch.pipeline().addLast(AGGREGATOR_HANDLER_NAME, new HttpObjectAggregator(512 * 1024));
                ch.pipeline().addLast(HTTP_REQUEST_HANDLER_NAME, injector.get().getInstance(HttpHandler.class));

                if (compress()) {
                    ch.pipeline().addAfter(CODEC_HANDLER_NAME, COMPRESSOR_HANDLER_NAME, new HttpContentCompressor());
                }

            }
        };
    }

    protected boolean compress() {
        return false;
    }

    public static WebSocketServerConnectorBuilder builder() {
        return new WebSocketServerConnectorBuilder();
    }

    public static class WebSocketServerConnectorBuilder {
        int _port = 8080;
        Optional<Injector> _injector = Optional.empty();

        public WebSocketServerConnectorBuilder port(int port) {
            _port = port;
            return this;
        }

        public WebSocketServerConnectorBuilder injector(final Injector injector) {
            _injector = Optional.of(injector);
            return this;
        }

        public HttpServerConnector build() {
            return new HttpServerConnector(_port, _injector);
        }
    }
}
