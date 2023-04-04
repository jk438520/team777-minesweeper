package com.io.minesweeper.game_engine;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    public final Game.GameStatus gameStatus;

    List<FieldToDisplay> fieldEvents;

    public GameState(Game.GameStatus status, List<FieldToDisplay> fieldEvents) {
        gameStatus = status;
        this.fieldEvents = fieldEvents;
    }

    public GameState(Game.GameStatus status) {
        this.gameStatus = status;
        this.fieldEvents = new ArrayList<>();
    }

}
