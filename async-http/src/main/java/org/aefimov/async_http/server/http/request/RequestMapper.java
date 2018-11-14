package org.aefimov.async_http.server.http.request;

public interface RequestMapper {

    void register(Key key, RequestHandler requestHandler);

    RequestHandler resolve(Key key);

}
