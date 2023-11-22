package edu.uiuc.cs427app;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * This test case address the following actions:
 * start with mainActivity page with 2 cities, Dallas and Houston
 * click on weather button next to Dallas, weather of Dallas should show up
 * click top toolbar and back to mainActivity page
 * click on weather button next to Houston, weather of Houston should show up
 * click top toolbar and back to mainActivity page
 * done
 */

public class WeatherFeatureTest {
    private static final String TEST_USERNAME = "testUser";
    private static final String TEST_PASSWORD = "testPassword";

    private static final List<String> TEST_FAVOR_CITIES = new ArrayList<>(Arrays.asList("Dallas", "Houston"));
    private static final String TEST_THEME = "Theme 2";

    @Rule
    public ActivityScenarioRule<MainActivity> activityActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        // Clear SharedPreferences before each test to ensure a clean state
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString("username", TEST_USERNAME);
        editor.putString("password", TEST_PASSWORD);
        editor.putStringSet("cities", new HashSet<>(TEST_FAVOR_CITIES));
        editor.putString("theme", TEST_THEME);
        editor.apply(); // Use apply() instead of commit()
    }

    @Test
    public void testWeatherFeatureFor2Cities() {

        // test for first city, which is Dallas
        testWeatherDataForNthCity(0);

        // test for second city, which is Houston
        testWeatherDataForNthCity(1);
    }

    private static void testWeatherDataForNthCity(int number) {
        // find the weather button for first city, which is dal
        ViewInteraction button = onView(withNthInstanceOf(allOf(withText("WEATHER"), isAssignableFrom(Button.class)), number));
        button.perform(click());

        // WAIT
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        // Here you would assert the presence of weather data

        // Assert that the text of cityName is displayed
        onView(withText(Matchers.containsString("name: ")))
                .check(ViewAssertions.matches(isDisplayed()));

        // Assert that the text of time is displayed
        onView(withText(Matchers.containsString("Time: ")))
                .check(ViewAssertions.matches(isDisplayed()));

        // Assert that the text of temperature is displayed
        onView(withText(Matchers.containsString("Temperature: ")))
                .check(ViewAssertions.matches(isDisplayed()));

        // Assert that the text of humidity is displayed
        onView(withText(Matchers.containsString("Humidity: ")))
                .check(ViewAssertions.matches(isDisplayed()));

        // go back to main page
        ViewInteraction backButton = Espresso.onView(
                ViewMatchers.withId(R.id.toolbar)
        );
        backButton.perform(click());
    }


    public static Matcher<View> withNthInstanceOf(final Matcher<View> matcher, final int n) {
        return new TypeSafeMatcher<View>() {
            int count = -1;  // Start counting from -1

            @Override
            public void describeTo(Description description) {
                description.appendText("with nth instance of: ");
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (matcher.matches(view)) {
                    count++;
                    if (count == n) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

}
