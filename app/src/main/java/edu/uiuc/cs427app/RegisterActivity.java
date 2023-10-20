package edu.uiuc.cs427app;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText etUsername;
    private EditText etPassword;
    private CheckBox[] cityCheckBoxes;
    private RadioGroup themeRadioGroup;
    private Button register;

    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        // bind everything with id
        toolbar = findViewById(R.id.toolbar);
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        themeRadioGroup = findViewById(R.id.themeRadioGroup);
        cityCheckBoxes = new CheckBox[4]; // Change the array size based on the number of cities
        cityCheckBoxes[0] = findViewById(R.id.checkBox1);
        cityCheckBoxes[1] = findViewById(R.id.checkBox2);
        cityCheckBoxes[2] = findViewById(R.id.checkBox3);
        cityCheckBoxes[3] = findViewById(R.id.checkBox4);
        register = findViewById(R.id.register);


        // set listener for go back button
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // set listener for register button
        register.setOnClickListener(view -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            // Retrieve selected theme from the checked RadioButton in the RadioGroup
            int selectedThemeId = themeRadioGroup.getCheckedRadioButtonId();
            String selectedTheme = "";
            if (selectedThemeId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedThemeId);
                selectedTheme = selectedRadioButton.getText().toString();
            }
            // Retrieve cities selected
            List<String> selectedCities = new ArrayList<>();
            for (CheckBox checkBox : cityCheckBoxes) {
                if (checkBox.isChecked()) {
                    selectedCities.add(checkBox.getText().toString());
                }
            }

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(RegisterActivity.this, "username and password cannot be empty",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegisterActivity.this, "registered for user " + username +
                                " with cities of " + selectedCities +
                                " with theme " + selectedTheme,
                        Toast.LENGTH_SHORT).show();

                // TODO: save user info and jump to MainActivity
                User user = new User(username, password, selectedCities, selectedTheme);

                // TODO: how can we share this user info when I jump to MainActivity?
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("username", username);
                edit.putString("password", password);
                edit.putStringSet("cities", new HashSet<>(selectedCities));
                edit.putString("theme", selectedTheme);
                edit.commit();

                // Prepare the ContentValues to be inserted into the ContentProvider
                ContentValues values = new ContentValues();
                values.put(UserContract.COLUMN_USERNAME, user.getUsername());
                values.put(UserContract.COLUMN_PASSWORD, user.getPassword());
                values.put(UserContract.COLUMN_FAVORED_CITIES, TextUtils.join(",", user.getFavoredCities()));
                values.put(UserContract.COLUMN_THEME, user.getTheme());

                // Insert data into the ContentProvider
                // TODO: check username is not in database first
                getContentResolver().insert(UserContentProvider.CONTENT_URI, values);

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}