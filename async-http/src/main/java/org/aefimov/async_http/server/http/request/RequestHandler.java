package org.aefimov.async_http.server.http.request;

import io.netty.handler.codec.http.FullHttpResponse;

public interface RequestHandler {

    FullHttpResponse handle(Request request);

}
