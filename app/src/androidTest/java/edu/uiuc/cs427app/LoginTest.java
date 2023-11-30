package edu.uiuc.cs427app;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;

import android.text.TextUtils;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.*;

/*
./gradlew createDebugCoverageReport
for generating report
 */

public class LoginTest {

    private static final String TEST_USERNAME = "testUser";
    private static final String TEST_PASSWORD = "testPassword";

    private static final List<String> TEST_FAVOR_CITIES = new ArrayList<>(Arrays.asList("Dallas", "Houston"));
    private static final String TEST_THEME = "Theme 1";

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    /*
    set up before the test, we need to register the TestUser here before we can login.
     */
    @Before
    public void setUp() {
        // Clear SharedPreferences before each test to ensure a clean state
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Insert test user data into ContentProvider
        insertTestUser();
    }


    /*

     */
    private void insertTestUser() {
        // Prepare the ContentValues to be inserted into the ContentProvider
        ContentValues values = new ContentValues();
        values.put(UserContract.COLUMN_USERNAME, TEST_USERNAME);
        values.put(UserContract.COLUMN_PASSWORD, TEST_PASSWORD);
        values.put(UserContract.COLUMN_FAVORED_CITIES, TextUtils.join(",", TEST_FAVOR_CITIES));
        values.put(UserContract.COLUMN_THEME, TEST_THEME);
        // Insert the test user data into the ContentProvider
        ContentResolver contentResolver = InstrumentationRegistry.getInstrumentation().getTargetContext().getContentResolver();
        contentResolver.insert(UserContentProvider.CONTENT_URI, values);
    }

    @Test
    public void testUserLogin() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Type the username and password
        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.typeText(TEST_USERNAME));
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.typeText(TEST_PASSWORD));

        // Close the keyboard
        Espresso.closeSoftKeyboard();

        // Perform login by clicking the login button
        Espresso.onView(ViewMatchers.withId(R.id.login)).perform(ViewActions.click());

        // Wait for a while (you can adjust this delay based on your app's response time)
        // This is added to visually observe the test execution. In real tests, you might want to use IdlingResource.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if the MainActivity is launched after successful login
        Espresso.onView(ViewMatchers.withId(R.id.textView3)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.textView4)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.cityButtonLayout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Validate SharedPreferences data after successful login
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username", null);
        Set<String> savedCities = sharedPreferences.getStringSet("cities", new HashSet<>());

        // Check if the saved data matches the input data
        assert savedUsername != null && savedUsername.equals(TEST_USERNAME);
        assert savedCities.contains("Dallas"); // Assuming "default_city" is added to SharedPreferences during registration
    }


}
