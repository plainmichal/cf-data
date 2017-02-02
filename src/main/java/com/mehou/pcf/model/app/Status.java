package com.mehou.pcf.model.app;

/**
 * Created by michaltokarz on 27/01/2017.
 */
public enum Status {
    RUNNING("RUNNING"), STOPPED("STOPPED"), CRASHED("CRASHED"), UNKNOWN("UNKNOWN");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public static Status getEnum(String value) {
        for(Status v : values())
            if(v.status.equalsIgnoreCase(value)) return v;
        return UNKNOWN;
    }
}
