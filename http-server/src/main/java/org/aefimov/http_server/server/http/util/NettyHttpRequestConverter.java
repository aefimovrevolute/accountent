package org.aefimov.http_server.server.http.util;

import com.google.inject.Singleton;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;
import org.aefimov.http_server.server.http.request.Request;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Singleton
public class NettyHttpRequestConverter implements RawRequestConverter {
    @Override
    public Request parse(FullHttpRequest httpRequest) {
        try {
            URI uri = new URI(httpRequest.uri());
            Request rq = new Request();
            rq.setHttpMethod(httpRequest.method());
            rq.setPath(uri.getPath());
            ByteBuf content = httpRequest.content();
            if (content != null) {
                rq.setBody(content.toString(CharsetUtil.UTF_8));
            }
            String query = uri.getQuery();
            if (StringUtils.isNotBlank(query)) {
                rq.setParameters(makeParametersMap(query));
            }
            return rq;
        } catch (UnsupportedEncodingException | URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    private Map<String, String> makeParametersMap(String query) throws UnsupportedEncodingException {
        Map<String, String> queryPairs = new LinkedHashMap<>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            queryPairs.put(URLDecoder.decode(
                    pair.substring(0, idx), "UTF-8"),
                    URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return queryPairs;
    }
}
