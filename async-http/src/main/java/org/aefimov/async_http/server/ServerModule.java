package org.aefimov.async_http.server;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.aefimov.async_http.server.http.HttpServer;
import org.aefimov.async_http.server.http.request.RequestMapper;
import org.aefimov.async_http.server.http.request.RequestMapperDefault;
import org.aefimov.async_http.server.http.util.NettyHttpRequestConverter;
import org.aefimov.async_http.server.http.util.RawRequestConverter;

public class ServerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Integer.class).annotatedWith(Names.named(HttpServer.LISTEN_PORT_NAME))
                .toInstance(7899);
        bind(RawRequestConverter.class).to(NettyHttpRequestConverter.class);
        bind(RequestMapper.class).to(RequestMapperDefault.class);
    }

}
