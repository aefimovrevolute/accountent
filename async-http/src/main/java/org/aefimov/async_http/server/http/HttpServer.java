package org.aefimov.async_http.server.http;

import com.google.inject.Injector;
import org.aefimov.async_http.server.BasicNettyServer;
import org.aefimov.async_http.server.ServerConnector;

import javax.inject.Inject;
import javax.inject.Named;

public class HttpServer extends BasicNettyServer {

    public static final String LISTEN_PORT_NAME = "listenPort";


    @Inject
    public HttpServer(final Injector injector, @Named(LISTEN_PORT_NAME) int port) {
        super(HttpServerConnector.builder()
                .port(port)
                .injector(injector)
                .build());
    }

    public HttpServer(ServerConnector connector) {
        super(connector);
    }

    public HttpServer() {
        super(HttpServerConnector.builder()
                .port(8808)
                .build());
    }
}
