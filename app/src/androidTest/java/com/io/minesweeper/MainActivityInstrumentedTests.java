package com.io.minesweeper;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static com.io.minesweeper.EspressoTestsMatchers.withDrawable;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityInstrumentedTests {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    private ViewInteraction restartButton;
    private ViewInteraction setFlagButton;

    @Before
    public void setUp() {
        restartButton = onView(withId(R.id.restart));
        setFlagButton = onView(withId(R.id.setFlag));
    }

    @Test
    public void testRendersProperly() {
        restartButton.perform(click());
        onView(withId(R.id.boardView)).check(matches(isDisplayed()));
        onView(withId(R.id.chronometer)).check(matches(isDisplayed()));
        onView(withId(R.id.restart)).check(matches(isDisplayed()));
        onView(withId(R.id.setFlag)).check(matches(isDisplayed()));
        onView(withId(R.id.boardView)).check(matches(hasChildCount(14)));
    }

    @Test
    public void testFirstTile() {
        ViewInteraction firstTile =  onView(withId(0));
        firstTile.perform(click());

        // getDrawable of firstTile and check if it is not a bomb
        firstTile.check(matches(not(withDrawable(R.drawable.bomb))));
    }

    @Test
    public void gameChronometerWorks() {
        ViewInteraction chronometer = onView(withId(R.id.chronometer));
        chronometer.check(matches(withText("00:00")));

        // click on first tile
        ViewInteraction firstTile =  onView(withId(0));
        firstTile.perform(click());

        // sleep for 3 seconds
        try {
            Thread.sleep(3050);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // check if chronometer is running
        chronometer.check(matches(withText("00:03")));
    }

    private void endGame() {
        // get chronometer
        ViewInteraction chronometer = onView(withId(R.id.chronometer));
        for (int i = 0; i < 14 * 10; i++) {
            ViewInteraction tile = onView(withId(i));
            tile.perform(click());

            // check if game is over
            try {
                tile.check(matches(withDrawable(R.drawable.bomb)));

                return;
            } catch (AssertionError e) {
                // continue
            }
        }
    }

    @Test
    public void testGameEnds() {
        endGame();

        // all tiles are not blanks
        for (int i = 0; i < 14 * 10; i++) {
            ViewInteraction tile = onView(withId(i));
            tile.check(matches(not(withDrawable(R.drawable.blank))));
        }
    }

    @Test
    public void testRestart() {
        endGame();

        restartButton.perform(click());

        // check if all tiles are blanks
        for (int i = 0; i < 14 * 10; i++) {
            ViewInteraction tile = onView(withId(i));
            tile.check(matches(withDrawable(R.drawable.blank)));
        }
    }

    @Test
    public void testFlag() {
        ViewInteraction firstTile =  onView(withId(0));
        setFlagButton.perform(click());

        firstTile.check(matches(withDrawable(R.drawable.blank)));

        firstTile.perform(click());

        // check if flag is set
        firstTile.check(matches(withDrawable(R.drawable.flag)));
    }

    @Test
    public void testFlagToggle() {
        ViewInteraction firstTile =  onView(withId(0));
        setFlagButton.perform(click());

        firstTile.check(matches(withDrawable(R.drawable.blank)));

        firstTile.perform(click());

        // check if flag is set
        firstTile.check(matches(withDrawable(R.drawable.flag)));

        firstTile.perform(click());

        // check if flag is unset
        firstTile.check(matches(withDrawable(R.drawable.blank)));
    }

    @Test
    public void testFlagButton() {
        ViewInteraction firstTile =  onView(withId(0));
        setFlagButton.check(matches(withDrawable(R.drawable.bomb)));
        setFlagButton.perform(click());
        setFlagButton.check(matches(withDrawable(R.drawable.flag)));
        setFlagButton.perform(click());
        setFlagButton.check(matches(withDrawable(R.drawable.bomb)));
    }
}
