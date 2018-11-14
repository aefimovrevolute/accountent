package org.aefimov.async_http.server.http.util;

public enum ContentType {
    APPLICATION_JSON("application/json"), TEXT_HTML("text/html");

    private String type;

    ContentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
