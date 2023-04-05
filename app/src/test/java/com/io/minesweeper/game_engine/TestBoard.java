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
        boolean[][] arr = {
                {true, false},
                {false, false}
        };
        Game game = new Game(arr);
        GameState gs = game.click(0, 0);
        assert gs.gameStatus == Game.GameStatus.LOST;
        for (int row = 0; row < arr.length; row++)
            for (int column = 0; column < arr[0].length; column++) {
                int finalRow = row;
                int finalColumn = column;
                assert gs.fieldEvents.stream().anyMatch(
                        fieldToDisplay -> fieldToDisplay.row == finalRow
                                && fieldToDisplay.col == finalColumn
                                && arr[finalRow][finalColumn]
                                ? fieldToDisplay.event == FieldEvent.REVEAL_MINE
                                : fieldToDisplay.event == FieldEvent.REVEAL_NUMBER);
            }
    }
    
    
    @Test
    public void testWinning() {
        boolean[][] arr = {
                {true, false},
                {false, false}
        };
        Game game = new Game(arr);
        GameState gs = game.click(1, 1);
        assert gs.gameStatus == Game.GameStatus.PLAYING;
        assert gs.fieldEvents.size() == 1;
        FieldToDisplay ftd = gs.fieldEvents.get(0);
        assert ftd.event == FieldEvent.REVEAL_NUMBER;
        assert ftd.row == 1;
        assert ftd.col == 1;
        assert ftd.value == 1;
        gs = game.click(0, 1);
        assert gs.gameStatus == Game.GameStatus.PLAYING;
        assert gs.fieldEvents.size() == 1;
        ftd = gs.fieldEvents.get(0);
        assert ftd.event == FieldEvent.REVEAL_NUMBER;
        assert ftd.row == 0;
        assert ftd.col == 1;
        assert ftd.value == 1;
        gs = game.click(1, 0);
        assert gs.gameStatus == Game.GameStatus.WON;
        assert gs.fieldEvents.size() == 2;
        assert gs.fieldEvents.stream().anyMatch(
                fieldToDisplay -> fieldToDisplay.row == 1
                        && fieldToDisplay.col == 0
                        && fieldToDisplay.event == FieldEvent.REVEAL_NUMBER);
        assert gs.fieldEvents.stream().anyMatch(
                fieldToDisplay -> fieldToDisplay.row == 0
                        && fieldToDisplay.col == 0
                        && fieldToDisplay.event == FieldEvent.REVEAL_MINE);

    }

    @Test
    public void testWinningWithFlag() {
        boolean[][] arr = {
                {true, false},
                {false, false}
        };
        Game game = new Game(arr);
        GameState gs = game.click(1, 1);
        assert gs.gameStatus == Game.GameStatus.PLAYING;
        assert gs.fieldEvents.size() == 1;
        FieldToDisplay ftd = gs.fieldEvents.get(0);
        assert ftd.event == FieldEvent.REVEAL_NUMBER;
        assert ftd.row == 1;
        assert ftd.col == 1;
        assert ftd.value == 1;
        gs = game.click(0, 1);
        assert gs.gameStatus == Game.GameStatus.PLAYING;
        assert gs.fieldEvents.size() == 1;
        ftd = gs.fieldEvents.get(0);
        assert ftd.event == FieldEvent.REVEAL_NUMBER;
        assert ftd.row == 0;
        assert ftd.col == 1;
        assert ftd.value == 1;
        game.setClickMode(Game.ClickMode.FLAG);
        gs = game.click(0, 0);
        game.setClickMode(Game.ClickMode.BOMB);
        assert gs.gameStatus == Game.GameStatus.PLAYING;
        assert gs.fieldEvents.size() == 1;
        ftd = gs.fieldEvents.get(0);
        assert ftd.event == FieldEvent.FLAG;
        assert ftd.row == 0;
        assert ftd.col == 0;
        assert ftd.value == 0;
        gs = game.click(1, 0);
        assert gs.gameStatus == Game.GameStatus.WON;
        assert gs.fieldEvents.size() == 1;
        ftd = gs.fieldEvents.get(0);
        assert ftd.event == FieldEvent.REVEAL_NUMBER;
        assert ftd.row == 1;
        assert ftd.col == 0;
        assert ftd.value == 1;

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

    @Test
    public void testClickingOnRevealedField() {
        boolean[][] arr = {
                {true, false},
                {false, false}
        };
        Game game = new Game(arr);
        GameState gs = game.click(1, 1);
        assert gs.gameStatus == Game.GameStatus.PLAYING;
        assert gs.fieldEvents.size() == 1;
        FieldToDisplay ftd = gs.fieldEvents.get(0);
        assert ftd.event == FieldEvent.REVEAL_NUMBER;
        assert ftd.row == 1;
        assert ftd.col == 1;
        assert ftd.value == 1;
        gs = game.click(1, 1);
        assert gs.gameStatus == Game.GameStatus.PLAYING;
        assert gs.fieldEvents.size() == 0;
    }

}
