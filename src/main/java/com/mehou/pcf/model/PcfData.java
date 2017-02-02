package com.mehou.pcf.model;

import com.mehou.pcf.model.org.Org;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by michaltokarz on 27/01/2017.
 */
@Getter
public final class PcfData {
    private final List<Org> orgs;

    private PcfData(Builder builder) {
        orgs = Collections.unmodifiableList(new ArrayList<>(builder.orgs));
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private List<Org> orgs;

        private Builder() {
        }

        public Builder withOrg(Org val) {
            orgs.add(val);
            return this;
        }

        public PcfData build() {
            return new PcfData(this);
        }
    }
}
