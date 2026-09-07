package com.baguetteisrotate.starling.mood;

import java.time.ZonedDateTime;

public class MoodEntry {

    private ZonedDateTime time;
    private int mood;

    public MoodEntry() {
    }

    public MoodEntry(ZonedDateTime time, int mood) {
        this.time = time;
        this.mood = mood;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }
}