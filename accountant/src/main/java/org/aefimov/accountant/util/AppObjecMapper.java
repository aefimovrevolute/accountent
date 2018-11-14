package org.aefimov.accountant.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Singleton;

@Singleton
public class AppObjecMapper {

    private final ObjectMapper mapper;

    public AppObjecMapper() {
        this.mapper = new ObjectMapper();
    }

    public ObjectMapper instance() {
        return mapper;
    }
}
