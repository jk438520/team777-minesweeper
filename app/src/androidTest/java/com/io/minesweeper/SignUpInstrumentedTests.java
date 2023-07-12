package com.io.minesweeper;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.core.IsNot.not;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SignUpInstrumentedTests {

    @Rule
    public ActivityScenarioRule<SignUp> activityRule = new ActivityScenarioRule<>(SignUp.class);

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
        onView(withId(R.id.et_name)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_add)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_del)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_play)).check(matches(isDisplayed()));
    }

    @Test
    public void addPlayer() {
        onView(withId(R.id.et_name)).perform(click());
        onView(withId(R.id.et_name)).perform(typeText("wojtyla"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.btn_add)).perform(click());
        onView(withText("WOJTYLA")).check(matches(isDisplayed()));
    }

    @Test
    public void addPlayerAndPlay() {
        onView(withId(R.id.et_name)).perform(click());
        onView(withId(R.id.et_name)).perform(typeText("wojtyla"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.btn_add)).perform(click());

        onView(withText("WOJTYLA")).perform(click());
        onView(withId(R.id.btn_play)).perform(click());
        // check if intent is launched
        intended(hasComponent(LevelActivity.class.getName()));
    }

    @Test
    public void deletePlayer() {
        onView(withId(R.id.et_name)).perform(click());
        onView(withId(R.id.et_name)).perform(typeText("wojtyla"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.btn_add)).perform(click());

        onView(withId(R.id.btn_del)).perform(click());
        onView(withText("WOJTYLA")).perform(click());

        onView(withText("WOJTYLA")).check(doesNotExist());
    }
}