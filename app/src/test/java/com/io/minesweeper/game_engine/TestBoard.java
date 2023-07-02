package com.io.minesweeper.game_engine;

import static com.io.minesweeper.game_engine.FieldToDisplay.FieldEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Collection;
import java.util.stream.Collectors;

public class TestBoard {


    @Test
    public void testSimpleFieldReveal() {
        int x = 10;
        int y = 10;
        int minesCount = 10;
        Game game = new Game(x, y, minesCount);
        GameState gs = game.click(0, 0);
        Collection<FieldToDisplay> fieldEvents = gs.fieldEvents.stream().filter(
                ftd -> ftd.row == 0 && ftd.col == 0 && ftd.event == FieldEvent.REVEAL_NUMBER
        ).collect(Collectors.toList());
        assertEquals(fieldEvents.size(), 1);
    }

    // Test if Game(x, y, minesCount) generetes correct number of mines
    @Test
    public void testMinesCount() {
        int x = 10;
        int y = 10;
        int minesCount = 10;
        Game game = new Game(x, y, minesCount);
        GameState gs = game.click(0, 0);
        assertTrue(gs.fieldEvents.size() >= 1);
        int minesCountInGame = 0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (game.getField(i, j).isMine()) {
                    minesCountInGame++;
                }
            }
        }
        assertEquals(minesCountInGame, minesCount);
    }

    // test clicking on mine
    @Test
    public void testClickOnMine() {
        boolean[][] bombs = {
                {false, false},
                {false, true}
        };
        Game game = new Game(bombs);
        GameState gs = game.click(1, 1);
        assertEquals(4, gs.fieldEvents.size());
        assertEquals(Game.GameStatus.LOST, gs.gameStatus);
        assertEquals(Game.GameStatus.LOST, game.getGameStatus());

    }

    // test winning
    @Test
    public void testWinning() {
        boolean[][] bombs = {
                {false, false},
                {false, true}
        };
        Game game = new Game(bombs);
        game.click(0, 0);
        game.click(0, 1);
        GameState gs = game.click(1, 0);
        assertEquals(Game.GameStatus.WON, gs.gameStatus);
    }

    // test toggle flag

    @Test
    public void testToggleFlag() {
        int x = 10;
        int y = 10;
        int minesCount = 10;
        Game game = new Game(x, y, minesCount);
        assertEquals(Game.ClickMode.BOMB, game.getClickMode());
        game.setClickMode(Game.ClickMode.FLAG);
        assertEquals(Game.ClickMode.FLAG, game.getClickMode());
        game.setClickMode(Game.ClickMode.BOMB);
        assertEquals(Game.ClickMode.BOMB, game.getClickMode());
    }

    //test flagging
    @Test
    public void testFlagging() {
        boolean[][] bombs = {
                {true, false, false},
                {false, false, false},
                {false, false, true}
        };
        Game game = new Game(bombs);
        game.setClickMode(Game.ClickMode.FLAG);
        GameState gs = game.click(0, 0);
        assertEquals(Game.GameStatus.PLAYING, gs.gameStatus);
        assertEquals(1, gs.fieldEvents.size());
        assertEquals(FieldEvent.FLAG, gs.fieldEvents.get(0).event);
    }

    //test click on revealed

    @Test
    public void testClickOnFlag() {
        boolean[][] bombs = {
                {true, false, false},
                {false, false, false},
                {false, false, true}
        };
        Game game = new Game(bombs);
        game.click(1, 0);
        game.setClickMode(Game.ClickMode.FLAG);
        game.click(0, 0);
        GameState gs = game.click(1, 0);
        assertEquals(4, gs.fieldEvents.size());
    }


    //test lazy flags
    @Test
    public void testLazyFlag() {
        int x = 10;
        int y = 10;
        int bombs = 10;
        Game game = new Game(x, y, bombs);
        game.setClickMode(Game.ClickMode.FLAG);
        game.click(0, 0);
        game.setClickMode(Game.ClickMode.BOMB);
        GameState gs = game.click(0, 0);
        assertEquals(0, gs.fieldEvents.size());
        gs = game.click(0, 1);
        assertTrue(0 < gs.fieldEvents.size());
        assertEquals(FieldEvent.REVEAL_NUMBER, gs.fieldEvents.get(0).event);
        gs = game.click(0, 0);
        assertEquals(0, gs.fieldEvents.size());
    }

    //test unflag
    @Test
    public void testUnflag() {
        int x = 10;
        int y = 10;
        int bombs = 10;
        Game game = new Game(x, y, bombs);
        game.setClickMode(Game.ClickMode.FLAG);
        game.click(0, 0);
        GameState gs = game.click(0, 0);
        assertEquals(1, gs.fieldEvents.size());
        assertEquals(FieldEvent.UNFLAG, gs.fieldEvents.get(0).event);
    }

}
