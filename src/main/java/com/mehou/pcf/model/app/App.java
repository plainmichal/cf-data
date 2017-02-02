package com.mehou.pcf.model.app;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by michaltokarz on 27/01/2017.
 */
@Getter
public final class App {
    private final String id;
    private final String name;
    private final Status status;
    private final String buildpack;
    private final int instances;
    private final int memoryLimit;
    private final int diskLimit;
    private final String routes;

//    private final String url;

    private App(Builder builder) {
        id = builder.id;
        name = builder.name;
        status = builder.status;
        buildpack = builder.buildpack;
        instances = builder.instances;
        memoryLimit = builder.memoryLimit;
        diskLimit = builder.diskLimit;
        routes = builder.routes;
//        url = builder.url;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private Status status;
        private String buildpack;
        private int instances;
        private int memoryLimit;
        private int diskLimit;
        private String routes;
//        private String url;

        private Builder() {
        }

        public Builder withId(String val) {
            id = val;
            return this;
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withStatus(Status val) {
            status = val;
            return this;
        }

        public Builder withBuildpack(String val) {
            buildpack = val;
            return this;
        }

        public Builder withInstances(int val) {
            instances = val;
            return this;
        }

        public Builder withMemoryLimit(int val) {
            memoryLimit = val;
            return this;
        }

        public Builder withDiskLimit(int val) {
            diskLimit = val;
            return this;
        }

        public Builder withRoutes(String val) {
//            routes = Collections.unmodifiableList(new ArrayList<>(val));
            routes = val;
            return this;
        }

//        public Builder withUrl(String val) {
//            url = val;
//            return this;
//        }

        public App build() {
            return new App(this);
        }
    }
}
