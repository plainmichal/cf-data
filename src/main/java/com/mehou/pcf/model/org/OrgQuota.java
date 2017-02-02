package com.mehou.pcf.model.org;

import lombok.Getter;

/**
 * Created by michaltokarz on 27/01/2017.
 */
@Getter
public final class OrgQuota {
    private final float used;
    private final float limit;

    private OrgQuota(Builder builder) {
        used = builder.used;
        limit = builder.limit;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private float used;
        private float limit;

        private Builder() {
        }

        public Builder withUsed(float val) {
            used = val;
            return this;
        }

        public Builder withLimit(float val) {
            limit = val;
            return this;
        }

        public OrgQuota build() {
            return new OrgQuota(this);
        }
    }
}
