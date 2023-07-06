package com.io.minesweeper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;


import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LevelActivityInstrumentedTests {

    @Rule
    public ActivityScenarioRule<LevelActivity> activityRule = new ActivityScenarioRule<>(LevelActivity.class);

    // before set up init
    @Before
    public void setUp() {
        Intents.init();
    }

    // after set up release
    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testRendersProperly() {
        onView(withId(R.id.easy)).check(matches(isDisplayed()));
        onView(withId(R.id.medium)).check(matches(isDisplayed()));
        onView(withId(R.id.hard)).check(matches(isDisplayed()));
        onView(withId(R.id.custom)).check(matches(isDisplayed()));
    }

    @Test
    public void easyButtonStartsGame() {
        onView(withId(R.id.easy)).perform(click());
        onView(withId(R.id.boardView)).check(matches(isDisplayed()));
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void mediumButtonStartsGame() {
        onView(withId(R.id.medium)).perform(click());
        onView(withId(R.id.boardView)).check(matches(isDisplayed()));
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void hardButtonStartsGame() {
        onView(withId(R.id.hard)).perform(click());
        onView(withId(R.id.boardView)).check(matches(isDisplayed()));
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void customButtonGoesToCustomActivity() {
        onView(withId(R.id.custom)).perform(click());

        intended(hasComponent(CustomActivity.class.getName()));
    }

}
