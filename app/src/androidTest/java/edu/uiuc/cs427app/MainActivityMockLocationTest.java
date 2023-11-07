package edu.uiuc.cs427app;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityMockLocationTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testMockLocation() {
        // Click on the "Map" button for Chicago
        ViewInteraction chicagoMapButton = Espresso.onView(ViewMatchers.withText("Chicago"));
        chicagoMapButton.perform(ViewActions.click());

        // Mock the location to Champaign
        // You need to implement a method to mock the location in your app, for example, by using Mockito
        // Here, we'll mock the showMap method in the MainActivity
        mActivityRule.getScenario().onActivity(mainActivity -> {
            MainActivity spyMainActivity = Mockito.spy(mainActivity);
            Mockito.doNothing().when(spyMainActivity).showMap("Champaign");
        });

        // Click on the "Weather" button for Champaign
        ViewInteraction champaignWeatherButton = Espresso.onView(ViewMatchers.withText("Weather"));
        champaignWeatherButton.perform(ViewActions.click());

        // Check if the displayed city name matches the mocked location (Champaign)
        ViewInteraction champaignCityNameTextView = Espresso.onView(ViewMatchers.withId(R.id.cityName));
        champaignCityNameTextView.check(ViewAssertions.matches(ViewMatchers.withText("name: Champaign"))); // Replace with your expected city name format
    }
}
