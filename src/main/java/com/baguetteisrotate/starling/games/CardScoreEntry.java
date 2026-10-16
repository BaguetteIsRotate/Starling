package com.baguetteisrotate.starling.games;

import java.time.ZonedDateTime;

import com.baguetteisrotate.starling.Entry;

public class CardScoreEntry extends Entry {
    public CardScoreEntry() {
    }

    public CardScoreEntry(ZonedDateTime time, int score) {
        super(time, score);
    }
}
