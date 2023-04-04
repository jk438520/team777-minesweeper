package com.io.minesweeper.game_engine;

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
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (game.getField(i, j).isMine()) {
                    minesCountInGame++;
                }
            }
        }
        assert minesCountInGame == minesCount;
    }


    @Test
    public void testLoosing() {
        int x = 10;
        int y = 10;
        int minesCount = 10;
        Game game = new Game(x, y, minesCount);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (game.getField(i, j).isMine()) {
                    GameState up = game.click(i, j);
                    assert up.gameEnded;
                    assert !up.gameWon;
                }
            }
        }
        assert false;
    }

    @Test
    public void testNumberOnField() {
        //generate static board using boolean[][]  arr = {}
        boolean[][] arr = {
                {false, true, false, true, false},
                {false, true, false, true, false},
                {false, true, true, true, false},
                {false, true, true, false, false},
                {false, true, false, false, false}
        };
        Game game = new Game(arr);
        assert game.getNumberOnField(0, 0) == 2;
        assert game.getNumberOnField(2, 4) == 2;
        assert game.getNumberOnField(4, 2) == 3;
        assert game.getNumberOnField(4, 4) == 0;


    }

}
