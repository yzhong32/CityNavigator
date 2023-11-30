package edu.uiuc.cs427app;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import androidx.test.platform.app.InstrumentationRegistry;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.reset;

/**
 * In this test, we start with click on Weather button for Dallas, and then after showing weather for Dallas,
 * We mock to show Houston data, and confirm data is there.
 *
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MockingLocationTest {

    private static final String TEST_USERNAME = "testUser";
    private static final String TEST_PASSWORD = "testPassword";

    private static final List<String> TEST_FAVOR_CITIES = new ArrayList<>(Arrays.asList("Dallas", "Houston"));
    private static final String TEST_THEME = "Theme 1";

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<>(MainActivity.class);


    // Use MockitoJUnit rule to initialize mocks
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


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
    public void testMockLocation() throws InterruptedException {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        editText.perform(replaceText("Dallas"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(android.R.id.button1), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton3.perform(scrollTo(), click());


        ViewInteraction textView = onView(
                allOf(withText("Dallas"),
                        withParent(withParent(withId(R.id.cityButtonLayout))),
                        isDisplayed()));

        //Assertion check to check added city name
        textView.check(matches(withText("Dallas")));


        // Click on the "Map" button for Chicago
        String cityNameToFind = "Dallas"; // Replace with the city name you want to find
        ViewInteraction chicagoMapButton = Espresso.onView(
                Matchers.allOf(
                        ViewMatchers.withText("Weather"),
                        ViewMatchers.withTagValue(Matchers.is((Object) cityNameToFind))
                )
        );
        chicagoMapButton.perform(ViewActions.click());
        // Log a message to check if the "Weather" button click is executed
        Log.d("MockLocationTest", "Clicked on Weather for Dallas");

        // Mock the location to Champaign
        // You need to implement a method to mock the location in your app, for example, by using Mockito
        // Here, we'll mock the showMap method in the MainActivity
//
        // Mock the location to Champaign
        mActivityRule.getScenario().onActivity(mainActivity -> {
            Log.d("MockLocationTest", "Inside onActivity");
            MainActivity spyMainActivity = Mockito.spy(mainActivity);
//            Mockito.doNothing().when(spyMainActivity).showWeather("Houston"); // Mock location to Champaign
            mainActivity.showWeather("Houston"); // Mock location to Houston
            Log.d("MockLocationTest", "Mocked location to Houston");
        });

        Thread.sleep(5000); // Adjust the sleep duration if needed

        // Check if the displayed city name matches the mocked location (Champaign)
        ViewInteraction houstonCityNameTextView = Espresso.onView(ViewMatchers.withId(R.id.cityName));
        houstonCityNameTextView.check(ViewAssertions.matches(ViewMatchers.withText("name: Houston")));
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
