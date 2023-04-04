package com.io.minesweeper.game_engine;


public class FieldToDisplay {
    enum FieldEvent {
        REVEAL_MINE,
        REVEAL_NUMBER,
        FLAG,
        UNFLAG
    }

    public final int x;
    public final int y;
    public final int value;
    public final FieldEvent event;

    public FieldToDisplay(int x, int y, int value, FieldEvent event) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.event = event;
    }

    public FieldToDisplay(int x, int y, FieldEvent event) {
        this.x = x;
        this.y = y;
        this.value = 0;
        this.event = event;
    }
}
