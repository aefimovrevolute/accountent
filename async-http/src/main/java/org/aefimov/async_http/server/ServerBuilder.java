package org.aefimov.async_http.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.aefimov.async_http.server.http.HttpServer;
import org.aefimov.async_http.server.http.request.RequestHandler;
import org.aefimov.async_http.server.http.request.Key;
import org.aefimov.async_http.server.http.request.RequestMapper;
import org.aefimov.async_http.server.http.request.RequestMapperDefault;

import javax.inject.Inject;

public class ServerBuilder {

    private final Injector injector;
    private final RequestMapper requestMapper;

    public ServerBuilder() {
        injector = Guice.createInjector(new ServerModule());
        requestMapper = injector.getInstance(RequestMapper.class);
    }

    public ServerBuilder appendRequestHandler(Key key, RequestHandler requestHandler) {
        this.requestMapper.register(key, requestHandler);
        return this;
    }

    public Server build() {
       return this.injector.getInstance(HttpServer.class);
    }

}
