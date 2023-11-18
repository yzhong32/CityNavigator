package edu.uiuc.cs427app;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
./gradlew createDebugCoverageReport
for generating report
 */

public class AddCityTest {
    // constants
    private static final String TEST_USERNAME = "testUser";
    private static final String TEST_PASSWORD = "testPassword";
    private static final List<String> TEST_FAVOR_CITIES = new ArrayList<>(Arrays.asList("Dallas", "Houston"));
    private static final String TEST_THEME = "Theme 1";

    /*
    ActivityScenarioRule: This rule provides functional testing of a single MainActivity.
    It launches the activity before the test starts and shuts it down after the test.
     */
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    /*
    set up before the test, we need to register the TestUser here before we can login.
     */
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
    public void testAddCity() throws InterruptedException {
        String cityNameToAdd = "Nashville"; // Replace with the city name you want to find

        // click on the button to add a new city
        onView(withId(R.id.buttonAddLocation)).perform(click());
        Log.d("AddCityTest", "Clicked on Add Location Button");
//        addLocationButton.perform(ViewActions.click());

        onView(isAssignableFrom(EditText.class)).perform(typeText(cityNameToAdd));
        Log.d("AddCityTest", "Typed a City: " + cityNameToAdd);
        // click add
        onView(withText("Add")).perform(click());
        Log.d("AddCityTest", "Clicked on Add");

        // WAIT
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // retrieve updated list of favorite cities
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        Set<String> favoriteCitiesSet = sharedPreferences.getStringSet("cities", new HashSet<>());
        List<String> favoriteCitiesList = new ArrayList<>(favoriteCitiesSet);

        // Assert that the updated list contains the new city
        Log.d("AddCityTest", "favoriteCitiesList: " + favoriteCitiesList);
        assert favoriteCitiesList.contains(cityNameToAdd);



    }
}
