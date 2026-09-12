package com.baguetteisrotate.starling;

import java.time.ZonedDateTime;

public abstract class Entry {
    protected ZonedDateTime time;
    private int value;

    public Entry() {
    }

    protected Entry(ZonedDateTime time, int value) {
        this.time = time;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public ZonedDateTime getTime() {
        return time;
    }


}