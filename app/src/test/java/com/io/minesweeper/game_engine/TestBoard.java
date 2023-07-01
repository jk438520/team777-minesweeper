package com.io.minesweeper.game_engine;

import static com.io.minesweeper.game_engine.FieldToDisplay.FieldEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

    @Test
    public void testToBeFailed() {
        fail();
    }


}
