package com.mehou.pcf.model.space;

import com.mehou.pcf.model.app.App;
import com.mehou.pcf.model.service.Service;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by michaltokarz on 27/01/2017.
 */
@Getter
public final class Space {
    private final String id;
    private final String name;
    private final List<App> apps;
//    private final List<Service> services;

//    private final String url;

    private Space(Builder builder) {
        id = builder.id;
        name = builder.name;
        apps = Collections.unmodifiableList(new ArrayList<>(builder.apps));
//        services = Collections.unmodifiableList(new ArrayList<>(builder.services));
//        url = builder.url;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private List<App> apps = new ArrayList<>();
//        private List<Service> services = new ArrayList<>();
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

        public Builder withApp(App val) {
            apps.add(val);
            return this;
        }

//        public Builder withService(Service val) {
//            services.add(val);
//            return this;
//        }
//
//        public Builder withUrl(String val) {
//            url = val;
//            return this;
//        }

        public Space build() {
            return new Space(this);
        }
    }
}
