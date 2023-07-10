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

public class CustomActivityInstrumentedTests {

    @Rule
    public ActivityScenarioRule<CustomActivity> activityRule = new ActivityScenarioRule<>(CustomActivity.class);

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
        onView(withId(R.id.colPlus)).check(matches(isDisplayed()));
        onView(withId(R.id.colMinus)).check(matches(isDisplayed()));
        onView(withId(R.id.satMinus)).check(matches(isDisplayed()));
        onView(withId(R.id.satPlus)).check(matches(isDisplayed()));
    }

    @Test
    public void performingClicksWorks() {
        for (int i = 0; i < 15; i++){
            onView(withId(R.id.colPlus)).perform(click());
            onView(withId(R.id.satPlus)).perform(click());
        }
        for (int i = 0; i < 15; i++){
            onView(withId(R.id.colMinus)).perform(click());
            onView(withId(R.id.satMinus)).perform(click());
        }
    }

    @Test
    public void playWorks() {
        for (int i = 0; i < 3; i++){
            onView(withId(R.id.colPlus)).perform(click());
            onView(withId(R.id.satPlus)).perform(click());
        }
        onView(withId(R.id.play)).perform(click());
        onView(withId(R.id.boardView)).check(matches(isDisplayed()));
        intended(hasComponent(MainActivity.class.getName()));
    }

}
