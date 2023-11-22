package edu.uiuc.cs427app;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

public class WeatherFeatureTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testWeatherDataForDallas() {
        // find the weather button for city Dallas
        ViewInteraction button = onView(
                allOf(withText("weather"),
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


        // Click on the Dallas button
        Espresso.onView(ViewMatchers.withText("Dallas"))
                .perform(ViewActions.click());

        // Check if WeatherActivity is opened
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack()); // Assuming WeatherActivity opens

        // Here you would assert the presence of weather data for Dallas
        // This depends on how the data is displayed in WeatherActivity

        // Assert that the text "Dallas" is displayed
        Espresso.onView(ViewMatchers.withText("Dallas"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Assert that the text "temperature" is displayed
        Espresso.onView(ViewMatchers.withText("temperature"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // go back to main page
        ViewInteraction backButton = Espresso.onView(
                ViewMatchers.withId(R.id.toolbar)
        );
        backButton.perform(click());
    }

    public static Matcher<View> childAtPosition(
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
