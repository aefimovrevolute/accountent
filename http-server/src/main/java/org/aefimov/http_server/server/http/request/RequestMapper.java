package org.aefimov.http_server.server.http.request;

public interface RequestMapper {

    void register(Key key, RequestHandler requestHandler);

    RequestHandler resolve(Key key);

}
