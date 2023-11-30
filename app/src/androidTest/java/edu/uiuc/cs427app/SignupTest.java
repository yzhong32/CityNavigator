package edu.uiuc.cs427app;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.Random;

public class SignupTest {

    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*";
    private static final String ALLOWED_CHARS = LETTERS + LETTERS.toUpperCase() + NUMBERS + SYMBOLS;

    private final Random random = new Random();

    public String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALLOWED_CHARS.charAt(random.nextInt(ALLOWED_CHARS.length())));
        }
        return sb.toString();
    }

    @Rule
    public ActivityScenarioRule<RegisterActivity> activityRule = new ActivityScenarioRule<>(RegisterActivity.class);

    @Test
    public void testUserSignUp() {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String TEST_USERNAME = generateRandomString(15);
        String TEST_PASSWORD = generateRandomString(15);


        // click the register button
        Espresso.onView(ViewMatchers.withId(R.id.register)).perform(ViewActions.click());

        // Type the username and password
        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.typeText(TEST_USERNAME));
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.typeText(TEST_PASSWORD));

        // Close the keyboard
        Espresso.closeSoftKeyboard();

        // Select favorite cities and theme
        Espresso.onView(ViewMatchers.withId(R.id.checkBox1)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.checkBox2)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.theme2RadioButton)).perform(ViewActions.click());

        // Register
        Espresso.onView(ViewMatchers.withId(R.id.register)).perform(ViewActions.click());

        Uri uri = Uri.parse("content://edu.uiuc.cs427app.provider/users");
        String[] projection = {UserContract.COLUMN_USERNAME, UserContract.COLUMN_PASSWORD, UserContract.COLUMN_FAVORED_CITIES, UserContract.COLUMN_THEME};
        String selection = UserContract.COLUMN_USERNAME + " = ? AND " + UserContract.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {TEST_USERNAME, TEST_PASSWORD};

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ContentResolver contentResolver = appContext.getContentResolver();

        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, null);
        Assert.assertNotNull("Cursor is null", cursor);
        Assert.assertEquals("Expected one row in the query result", 1, cursor.getCount());
        Assert.assertTrue("Cursor is empty or error occurred", cursor.moveToFirst());

        String foundUsername = cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_USERNAME));
        String foundPassword = cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_PASSWORD));
        String theme = cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_THEME));
        String cities = cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_FAVORED_CITIES));

        cursor.close();

        Assert.assertEquals("Username should be <TEST_USERNAME>", TEST_USERNAME, foundUsername);
        Assert.assertEquals("Password should be <TEST_PASSWORD>", TEST_PASSWORD, foundPassword);
        Assert.assertEquals("cities should be Champaign,Chicago", "Champaign,Chicago", cities);
        Assert.assertEquals("Theme should be Theme 2", "Theme 2", theme);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
