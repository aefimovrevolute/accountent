package org.aefimov.async_http.server.http.util;

import io.netty.handler.codec.http.FullHttpRequest;
import org.aefimov.async_http.server.http.request.Request;

public interface RawRequestConverter {

    Request parse(FullHttpRequest fullHttpRequest);

}
