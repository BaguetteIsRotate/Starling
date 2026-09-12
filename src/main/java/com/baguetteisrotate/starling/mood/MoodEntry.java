package com.baguetteisrotate.starling.mood;

import java.time.ZonedDateTime;
import com.baguetteisrotate.starling.Entry;

public class MoodEntry extends Entry {
    // private int mood;

    public MoodEntry() {
    }

    public MoodEntry(ZonedDateTime time, int mood) {
        super(time, mood);
        
        // this.mood = mood;
    }
}