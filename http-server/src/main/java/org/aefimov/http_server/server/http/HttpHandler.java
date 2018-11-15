package org.aefimov.http_server.server.http;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.concurrent.PromiseCombiner;
import org.aefimov.http_server.server.http.request.Key;
import org.aefimov.http_server.server.http.request.Request;
import org.aefimov.http_server.server.http.request.RequestHandler;
import org.aefimov.http_server.server.http.request.RequestMapper;
import org.aefimov.http_server.server.http.util.ContentType;
import org.aefimov.http_server.server.http.util.RawRequestConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static io.netty.buffer.Unpooled.copiedBuffer;

/**
 * The HTTP request dispatcher.
 */
public class HttpHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);

    private final RawRequestConverter rawRequestConverter;
    private final RequestMapper requestMapper;

    @Inject
    public HttpHandler(RawRequestConverter rawRequestConverter, RequestMapper requestMapper) {
        this.rawRequestConverter = rawRequestConverter;
        this.requestMapper = requestMapper;
    }

    /**
     * Dispatches the HTTP request by path to the mapped {@link RequestHandler} instances
     * which were registered in {@link RequestMapper}.
     *
     * @param ctx the channel context.
     * @param msg the full http request.
     * @throws Exception if an error occurs.
     * @see RequestHandler
     * @see RequestMapper
     * @see RawRequestConverter
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest httpRequest = (FullHttpRequest) msg;
            Request request = this.rawRequestConverter.parse(httpRequest);

            Key requestMappingKey = new Key(request.getPath(), request.getHttpMethod().name());
            RequestHandler handler = this.requestMapper.resolve(requestMappingKey);

            FullHttpResponse httpResponse;
            if (handler == null) {
                httpResponse = HttpResponder.createResponse(HttpResponseStatus.NOT_FOUND);
            } else {
                httpResponse = handler.handle(request);
            }
            writeResponse(ctx, httpResponse);
        }
    }

    private void writeResponse(ChannelHandlerContext ctx, FullHttpResponse httpResponse) {
        PromiseCombiner promiseCombiner = new PromiseCombiner();
        promiseCombiner.add(ctx.writeAndFlush(httpResponse).addListener((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                logger.info("Failed to write to channel: {}", future.cause());
            }
            if (future.isSuccess()) {
                logger.info("Successfully completed");
            }
        }));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.writeAndFlush(HttpResponder.createResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR, cause.getMessage(),
                ContentType.TEXT_HTML));
        logger.error("Channel exception caught", cause);
    }
}
