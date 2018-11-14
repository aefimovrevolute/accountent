package org.aefimov.async_http.server.http.request;

import io.netty.handler.codec.http.HttpMethod;

import java.util.Map;

public class Request {

    private HttpMethod httpMethod;

    private String path;

    private Map<String, String> parameters;

    private String body;

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Request{" +
                "httpMethod=" + httpMethod +
                ", path='" + path + '\'' +
                ", parameters=" + parameters +
                ", body='" + body + '\'' +
                '}';
    }
}
