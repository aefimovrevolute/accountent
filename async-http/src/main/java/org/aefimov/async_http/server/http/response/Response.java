package org.aefimov.async_http.server.http.response;

public class Response {

    private String contentType;

    private String entity;

    public Response(String contentType, String entity) {
        this.contentType = contentType;
        this.entity = entity;
    }

    public String getContentType() {
        return contentType;
    }

    public String getEntity() {
        return entity;
    }
}
