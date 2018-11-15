package org.aefimov.http_server.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.aefimov.http_server.server.http.HttpServer;
import org.aefimov.http_server.server.http.request.RequestHandler;
import org.aefimov.http_server.server.http.request.Key;
import org.aefimov.http_server.server.http.request.RequestMapper;

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
