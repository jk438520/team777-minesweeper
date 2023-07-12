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

public class UserActivityInstrumentedTests {

    @Rule
    public ActivityScenarioRule<UserActivity> activityRule = new ActivityScenarioRule<>(UserActivity.class);

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
        onView(withId(R.id.simpleTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_in)).check(matches(isDisplayed()));
        onView(withId(R.id.play_anonymously)).check(matches(isDisplayed()));
        onView(withId(R.id.show_ranking)).check(matches(isDisplayed()));
    }

    @Test
    public void signIn() {
        onView(withId(R.id.sign_in)).perform(click());
        intended(hasComponent(SignUp.class.getName()));
    }

    @Test
    public void playAnonymously() {
        onView(withId(R.id.play_anonymously)).perform(click());
        intended(hasComponent(LevelActivity.class.getName()));
    }

    @Test
    public void showRanking() {
        onView(withId(R.id.show_ranking)).perform(click());
        intended(hasComponent(RankingActivity.class.getName()));
    }

}
