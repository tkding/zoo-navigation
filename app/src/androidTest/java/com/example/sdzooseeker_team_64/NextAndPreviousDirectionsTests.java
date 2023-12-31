package com.example.sdzooseeker_team_64;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NextAndPreviousDirectionsTests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void nextButtonExistsTest() {
        DataInteraction materialTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.search_list_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
                .atPosition(1);
        materialTextView.perform(click());

        DataInteraction materialTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.search_list_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
                .atPosition(2);
        materialTextView2.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.plan_btn), withText("Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction button = onView(
                allOf(withId(R.id.next_btn), withText("NEXT"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));
    }

    @Test
    public void nextButtonTest() {
        DataInteraction materialTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.search_list_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
                .atPosition(1);
        materialTextView.perform(click());

        DataInteraction materialTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.search_list_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
                .atPosition(2);
        materialTextView2.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.plan_btn), withText("Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.next_btn), withText("NEXT"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

//        ViewInteraction textView = onView(
//                allOf(withId(R.id.direction_textView), withText("Set direction text for the next exhibit on the sorted list"),
//                        withParent(withParent(withId(android.R.id.content))),
//                        isDisplayed()));
//        textView.check(matches(isDisplayed()));
    }

    @Test
    public void prevButtonExistsTest() {
        DataInteraction materialTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.search_list_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
                .atPosition(1);
        materialTextView.perform(click());

        DataInteraction materialTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.search_list_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
                .atPosition(2);
        materialTextView2.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.plan_btn), withText("Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.next_btn), withText("NEXT"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.previous_btn), withText("PREVIOUS"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));
    }

    @Test
    public void prevButtonNotExistsAtFirstExhibitTest() {
        DataInteraction materialTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.search_list_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
                .atPosition(1);
        materialTextView.perform(click());

        DataInteraction materialTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.search_list_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
                .atPosition(2);
        materialTextView2.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.plan_btn), withText("Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction button = onView(
                allOf(withId(R.id.next_btn), withText("PREVIOUS"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button.check(doesNotExist());
    }

    @Test
    public void nextButtonNotExistsAtLastExhibitTest() {
        DataInteraction materialTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.search_list_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
                .atPosition(1);
        materialTextView.perform(click());

        DataInteraction materialTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.search_list_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
                .atPosition(2);
        materialTextView2.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.plan_btn), withText("Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.next_btn), withText("NEXT"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        materialButton2.check(doesNotExist());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
