package org.aefimov.async_http.server.http.request;

import com.google.inject.Singleton;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Singleton
public class RequestMapperDefault implements RequestMapper {

    private final ConcurrentMap<Key, RequestHandler> storage;

    public RequestMapperDefault() {
        storage = new ConcurrentHashMap<>();
    }

    @Override
    public void register(Key key, RequestHandler requestHandler) {
        this.storage.put(key, requestHandler);
    }

    @Override
    public RequestHandler resolve(Key key) {
        return this.storage.get(key);
    }
}
