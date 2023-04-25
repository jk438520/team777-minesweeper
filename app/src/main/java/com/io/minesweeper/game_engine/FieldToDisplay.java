package com.io.minesweeper.game_engine;


public class FieldToDisplay {
    public enum FieldEvent {
        REVEAL_MINE,
        REVEAL_NUMBER,
        FLAG,
        UNFLAG
    }

    public final int row;
    public final int col;
    public final int value;
    public final FieldEvent event;

    public FieldToDisplay(int row, int col, int value, FieldEvent event) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.event = event;
    }

    public FieldToDisplay(int row, int col, FieldEvent event) {
        this.row = row;
        this.col = col;
        this.value = 0;
        this.event = event;
    }
}
