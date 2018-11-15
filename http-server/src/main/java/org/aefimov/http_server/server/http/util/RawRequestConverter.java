package org.aefimov.http_server.server.http.util;

import io.netty.handler.codec.http.FullHttpRequest;
import org.aefimov.http_server.server.http.request.Request;

public interface RawRequestConverter {

    Request parse(FullHttpRequest fullHttpRequest);

}
