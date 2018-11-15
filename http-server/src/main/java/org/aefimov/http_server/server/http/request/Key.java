package org.aefimov.http_server.server.http.request;

import java.util.Objects;

public class Key {

    private String url;

    private String httpMethod;

    public Key(String url, String httpMethod) {
        this.url = url;
        this.httpMethod = httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key mapKey = (Key) o;
        return Objects.equals(url, mapKey.url) &&
                Objects.equals(httpMethod, mapKey.httpMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, httpMethod);
    }
}
