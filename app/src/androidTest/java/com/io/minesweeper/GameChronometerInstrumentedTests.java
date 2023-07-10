package com.io.minesweeper;

import android.widget.Chronometer;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class GameChronometerInstrumentedTests {
    private GameChronometer gameChronometer;
    private Chronometer chronometer;

    @Before
    public void setUp() {
        chronometer = new Chronometer(InstrumentationRegistry.getInstrumentation().getContext());
        gameChronometer = new GameChronometer(chronometer);
    }

    @Test
    public void testTileClick() {
        gameChronometer.tileClick();

        assertTrue(gameChronometer.isRunning());
    }

    @Test
    public void testStop() {
        gameChronometer.tileClick();
        gameChronometer.stop();

        assertFalse(gameChronometer.isRunning());
    }

    @Test
    public void testRestartClick() {
        gameChronometer.tileClick();
        gameChronometer.restartClick();

        assertFalse(gameChronometer.isRunning());
    }

    @Test
    public void testGetTimeInMillis() {
        gameChronometer.tileClick();
        // Wait for some time (e.g., 5 seconds)
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameChronometer.stop();

        long timeInMillis = gameChronometer.getTimeInMillis();

        assertTrue(timeInMillis >= 5000);
    }
}
