package org.aefimov.http_server.server.http.util;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import org.aefimov.http_server.server.http.request.Request;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NettyHttpRequestConverterTest {

    private DefaultFullHttpRequest fullHttpRequestMock = mock(DefaultFullHttpRequest.class);
    private ByteBuf byteBufMock = mock(ByteBuf.class);

    @Test
    public void parseFullFilledHttpRequest() {

        NettyHttpRequestConverter converter = new NettyHttpRequestConverter();

        when(byteBufMock.toString(any(Charset.class))).thenReturn("{}");
        when(fullHttpRequestMock.uri()).thenReturn("/api/rest?id=1");
        when(fullHttpRequestMock.content()).thenReturn(byteBufMock);

        Request request = converter.parse(fullHttpRequestMock);

        assertNotNull(request);
        assertEquals("{}", request.getBody());
        assertEquals("/api/rest", request.getPath());
        assertEquals("1", request.getParameters().get("id"));
    }

    @Test
    public void parseHttpRequestWithoutQuery() {

        NettyHttpRequestConverter converter = new NettyHttpRequestConverter();

        when(byteBufMock.toString(any(Charset.class))).thenReturn("{}");
        when(fullHttpRequestMock.uri()).thenReturn("/api/rest");
        when(fullHttpRequestMock.content()).thenReturn(byteBufMock);

        Request request = converter.parse(fullHttpRequestMock);

        assertNotNull(request);
        assertEquals("{}", request.getBody());
        assertEquals("/api/rest", request.getPath());
        assertNull(request.getParameters());
    }

    @Test
    public void parseHttpRequestWithoutContent() {

        NettyHttpRequestConverter converter = new NettyHttpRequestConverter();

        when(byteBufMock.toString(any(Charset.class))).thenReturn("{}");
        when(fullHttpRequestMock.uri()).thenReturn("/api/rest?id=1");
        when(fullHttpRequestMock.content()).thenReturn(null);

        Request request = converter.parse(fullHttpRequestMock);

        assertNotNull(request);
        assertNull(request.getBody());
        assertEquals("/api/rest", request.getPath());
        assertEquals("1", request.getParameters().get("id"));
    }

}