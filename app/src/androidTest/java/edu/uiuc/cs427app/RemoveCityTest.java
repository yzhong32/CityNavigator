package edu.uiuc.cs427app;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RemoveCityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testRemoveAnExistingCity() {
        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.register), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(android.R.id.content),
                                                childAtPosition(
                                                        withId(androidx.appcompat.R.id.decor_content_parent),
                                                        0)),
                                        0),
                                5),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.username),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0),
                                        2),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("hend"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0),
                                        3),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("123"), closeSoftKeyboard());

        ViewInteraction materialRadioButton = onView(
                allOf(withId(R.id.theme2RadioButton), withText("Theme 2"),
                        childAtPosition(
                                allOf(withId(R.id.themeRadioGroup),
                                        childAtPosition(
                                                childAtPosition(
                                                        withId(android.R.id.content),
                                                        0),
                                                6)),
                                1),
                        isDisplayed()));
        materialRadioButton.perform(click());

        ViewInteraction materialCheckBox = onView(
                allOf(withId(R.id.checkBox1), withText("Champaign"),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0),
                                        4),
                                0),
                        isDisplayed()));
        materialCheckBox.perform(click());

        ViewInteraction materialCheckBox2 = onView(
                allOf(withId(R.id.checkBox2), withText("Chicago"),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0),
                                        4),
                                1),
                        isDisplayed()));
        materialCheckBox2.perform(click());

        ViewInteraction materialCheckBox3 = onView(
                allOf(withId(R.id.checkBox3), withText("New York"),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0),
                                        4),
                                2),
                        isDisplayed()));
        materialCheckBox3.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.register), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(android.R.id.content),
                                                childAtPosition(
                                                        withId(androidx.appcompat.R.id.decor_content_parent),
                                                        0)),
                                        0),
                                7),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withText("New York"),
                        withParent(withParent(allOf(withId(R.id.cityButtonLayout),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))))),
                        isDisplayed()));

        // assert if New York is on the city list
        textView.check(matches(withText("New York")));

        ViewInteraction textView2 = onView(
                allOf(withText("New York"),
                        withParent(withParent(allOf(withId(R.id.cityButtonLayout),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))))),
                        isDisplayed()));
        textView2.check(matches(withText("New York")));

        ViewInteraction textView3 = onView(
                allOf(withText("Chicago"),
                        withParent(withParent(allOf(withId(R.id.cityButtonLayout),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))))),
                        isDisplayed()));
        textView3.check(matches(withText("Chicago")));

        ViewInteraction textView4 = onView(
                allOf(withText("Chicago"),
                        withParent(withParent(allOf(withId(R.id.cityButtonLayout),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))))),
                        isDisplayed()));
        textView4.check(matches(withText("Chicago")));

        ViewInteraction textView5 = onView(
                allOf(withText("Chicago"),
                        withParent(withParent(allOf(withId(R.id.cityButtonLayout),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))))),
                        isDisplayed()));

        textView5.check(matches(withText("Chicago")));

        // WAIT
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ViewInteraction button = onView(
                allOf(withText("Remove"),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.cityButtonLayout),
                                                childAtPosition(
                                                        withClassName(is("android.widget.LinearLayout")),
                                                        2)),
                                        0),
                                3),
                        isDisplayed()));
        button.perform(click());

        // WAIT
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Assert that New York is removed
        onView(ViewMatchers.withText("New York"))
                .check(ViewAssertions.doesNotExist());


        // Assert that "Champaign" is remain on the list
        onView(withText("Champaign")).check(matches(isDisplayed()));


        // Assert that "Champaign" is remain on the list
        onView(withText("Chicago")).check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withText("Remove"),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.cityButtonLayout),
                                                childAtPosition(
                                                        withClassName(is("android.widget.LinearLayout")),
                                                        2)),
                                        0),
                                3),
                        isDisplayed()));
        button2.perform(click());

        // Assert that Chicago is removed
        onView(ViewMatchers.withText("Chicago"))
                .check(ViewAssertions.doesNotExist());


        // Assert that "Champaign" is remain on the list
        onView(withText("Champaign")).check(matches(isDisplayed()));

        // WAIT
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


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
