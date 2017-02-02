package com.mehou.pcf.model.org;

import com.mehou.pcf.model.space.Space;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by michaltokarz on 27/01/2017.
 */
@Getter
public final class Org {
    private final String id;
    private final String name;
    private final OrgQuota quota;
    private final List<Space> spaces;

//    private final String url;

    private Org(Builder builder) {
        id = builder.id;
        name = builder.name;
        quota = builder.quota;
        spaces = Collections.unmodifiableList(new ArrayList<>(builder.spaces));
//        url = builder.url;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private OrgQuota quota;
        private List<Space> spaces = new ArrayList<>();
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

        public Builder withQuota(OrgQuota val) {
            quota = val;
            return this;
        }

        public Builder withSpace(Space val) {
            spaces.add(val);
            return this;
        }

//        public Builder withUrl(String val) {
//            url = val;
//            return this;
//        }

        public Org build() {
            return new Org(this);
        }
    }
}
