package org.aefimov.http_server.server.http.request;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * The interface that define the HTTP request handling.
 * You should implement this if you want to write your
 * owen request handler.
 * <p>
 * And you have to register an instance of implementation
 * in the {@link RequestMapper}.
 *
 * @see RequestMapperDefault
 */
public interface RequestHandler {

    FullHttpResponse handle(Request request);

}
