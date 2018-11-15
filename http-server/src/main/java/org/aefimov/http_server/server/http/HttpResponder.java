package org.aefimov.http_server.server.http;

import io.netty.handler.codec.http.*;
import org.aefimov.http_server.server.http.util.ContentType;

import static io.netty.buffer.Unpooled.copiedBuffer;

public abstract class HttpResponder {

    public static FullHttpResponse createSuccessResponse() {
        return createSuccessResponse(HttpResponseStatus.OK.reasonPhrase(), ContentType.TEXT_HTML);
    }

    public static FullHttpResponse createSuccessResponse(final String payload, ContentType contentType) {
        return createResponse(HttpResponseStatus.OK, payload, contentType);
    }

    public static FullHttpResponse createResponse(final HttpResponseStatus status) {
        return createResponse(status, status.reasonPhrase(), ContentType.TEXT_HTML);
    }

    public static FullHttpResponse createResponse(final HttpResponseStatus status, String payload,
                                                  ContentType contentType) {
        return createResponse(status, payload.getBytes(), contentType);
    }

    public static FullHttpResponse createResponse(final HttpResponseStatus status, byte[] payload,
                                                  ContentType contentType) {
        return createResponse(status, payload, HttpVersion.HTTP_1_1, contentType);
    }

    public static FullHttpResponse createResponse(final HttpResponseStatus status, byte[] payload,
                                                  final HttpVersion version, ContentType contentType) {
        if (payload.length > 0) {
            FullHttpResponse response = new DefaultFullHttpResponse(version, status, copiedBuffer(payload));
            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, payload.length);
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, contentType.getType());
            return response;
        } else {
            return new DefaultFullHttpResponse(version, status);
        }
    }

}
