package com.io.minesweeper.game_engine;

import static com.io.minesweeper.game_engine.FieldToDisplay.FieldEvent;

import org.junit.Test;

public class TestBoard {
    // Test if Game(x, y, minesCount) generetes correct number of mines
    @Test
    public void testMinesCount() {
        int x = 10;
        int y = 10;
        int minesCount = 10;
        Game game = new Game(x, y, minesCount);
        int minesCountInGame = 0;
        GameState gameState = game.click(0, 0);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (game.getField(i, j).isMine()) {
                    minesCountInGame++;
                }
            }
        }
        assert minesCountInGame == minesCount;
    }

}
