package edu.uiuc.cs427app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    // UI components
    private TextView register;
    private TextView login;
    private EditText etUsername;
    private EditText etPassword;


    /**
     * Called when the activity is started to set layout and content.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize UI components
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);

        // Set up the register button to navigate to the RegisterActivity
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            // Handles the click event for the login TextView
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Set up the login button to validate user credentials and navigate to the MainActivity
        login.setOnClickListener(new View.OnClickListener() {
            @Override

            //Handles the click event for the register TextView
            public void onClick(View view) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Validate user credentials using content provider
                User user = validateUserFromContentProvider(username, password);

                if (user != null) {
                    // User is valid, navigate to the MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Optional: Close the LoginActivity to prevent going back after logging in
                } else {
                    // Invalid credentials, show an error message (e.g., via Toast)
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * Validates the user's credentials against the content provider.
     *
     * @param username The entered username.
     * @param password The entered password.
     * @return User object if authentication is successful, null otherwise.
     */
    @SuppressLint("Range")
    private User validateUserFromContentProvider(String username, String password) {
        // Perform a query on the content provider to validate the user
        // Define the URI for user validation
        Uri uri = Uri.parse("content://edu.uiuc.cs427app.provider/users");

        // Define the projection (columns to retrieve) and selection (where clause)
        String[] projection = {UserContract.COLUMN_USERNAME, UserContract.COLUMN_PASSWORD, UserContract.COLUMN_FAVORED_CITIES, UserContract.COLUMN_THEME};
        String selection = UserContract.COLUMN_USERNAME + " = ? AND " + UserContract.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        // Perform the query
        Cursor cursor = getContentResolver().query(uri, projection, selection, selectionArgs, null);

        // Check if the cursor contains any rows (user found)
        if (cursor != null && cursor.moveToFirst()) {
            String foundUsername = cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_USERNAME));
            String foundPassword = cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_PASSWORD));
            String theme = cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_THEME));

            // Retrieve favored cities from the cursor and convert them to a List<String>
            String citiesString = cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_FAVORED_CITIES));
            List<String> favoredCities = new ArrayList<>(Arrays.asList(citiesString.split(",")));

            // Close the cursor after use
            cursor.close();

            // add to sharedPreference
            SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("username", username);
            edit.putString("password", password);
            edit.putStringSet("cities", new HashSet<>(favoredCities));
            edit.putString("theme", theme);
            edit.commit();

            // Create and return the User object
            return new User(foundUsername, foundPassword, favoredCities, theme);
        }

        // Close the cursor if no user was found
        if (cursor != null) {
            cursor.close();
        }

        // User not found, return null
        return null;
    }

}
