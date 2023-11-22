package edu.uiuc.cs427app;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
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
public class LocationFeatureTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testLocationFeatureFor2Cities() {
        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.register), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.username),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("srt6"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction materialCheckBox = onView(
                allOf(withId(R.id.checkBox1), withText("Champaign"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                0),
                        isDisplayed()));
        materialCheckBox.perform(click());

        ViewInteraction materialRadioButton = onView(
                allOf(withId(R.id.theme1RadioButton), withText("Theme 1"),
                        childAtPosition(
                                allOf(withId(R.id.themeRadioGroup),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                6)),
                                0),
                        isDisplayed()));
        materialRadioButton.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.register), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.buttonAddLocation), withText("Add a location"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                4),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction editText = onView(
                allOf(childAtPosition(
                                allOf(withId(android.R.id.custom),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                0)),
                                0),
                        isDisplayed()));

        // Adding City 1 - New York
        editText.perform(replaceText("New York"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(android.R.id.button1), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton3.perform(scrollTo(), click());


        ViewInteraction textView = onView(
                allOf(withText("New York"),
                        withParent(withParent(withId(R.id.cityButtonLayout))),
                        isDisplayed()));

        //Assertion check to check added city name
        textView.check(matches(withText("New York")));

        ViewInteraction button = onView(
                allOf(withText("Map"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.cityButtonLayout),
                                        1),
                                1),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.welcomeText), withText("Welcome to the New York"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));

        //Assertion check to check correct city is displayed after pressing the Map button
        textView2.check(matches(withText("Welcome to the New York")));

        ViewInteraction view = onView(
                allOf(withContentDescription("Google Map"),
                        withParent(withParent(withId(R.id.map))),
                        isDisplayed()));

        // Assertion check to check map is displayed correctly
        view.check(matches(isDisplayed()));

        ViewInteraction toolbar = onView(
                allOf(withId(R.id.toolbar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        toolbar.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.buttonAddLocation), withText("Add a location"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                4),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction editText2 = onView(
                allOf(childAtPosition(
                                allOf(withId(android.R.id.custom),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                0)),
                                0),
                        isDisplayed()));
                        
        // Adding City 2 - Mumbai
        editText2.perform(replaceText("Mumbai"), closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(android.R.id.button1), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton5.perform(scrollTo(), click());

        ViewInteraction textView3 = onView(
                allOf(withText("Mumbai"),
                        withParent(withParent(withId(R.id.cityButtonLayout))),
                        isDisplayed()));

        //Assertion check to check added city name
        textView3.check(matches(withText("Mumbai")));

        ViewInteraction button2 = onView(
                allOf(withText("Map"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.cityButtonLayout),
                                        2),
                                1),
                        isDisplayed()));
        button2.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.welcomeText), withText("Welcome to the Mumbai"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));

        //Assertion check to check correct city is displayed after pressing the Map button
        textView4.check(matches(withText("Welcome to the Mumbai")));

        ViewInteraction view2 = onView(
                allOf(withContentDescription("Google Map"),
                        withParent(withParent(withId(R.id.map))),
                        isDisplayed()));

        // Assertion check to check map is displayed correctly
        view2.check(matches(isDisplayed()));

        ViewInteraction toolbar2 = onView(
                allOf(withId(R.id.toolbar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        toolbar2.perform(click());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.logoutButton), withText("Logout"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                5),
                        isDisplayed()));
        materialButton6.perform(click());
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

