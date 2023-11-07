package edu.uiuc.cs427app;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import edu.uiuc.cs427app.databinding.ActivityMainBinding;

import java.util.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    String username;
    String password;
    List<String> citiesList;
    String theme;

    // called when the activity is started to set layout and content
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        // get user info
        // Access the SharedPreferences file with the same name used in the other activity
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        // Retrieve values from SharedPreferences
        username = sharedPreferences.getString("username", "");
        password = sharedPreferences.getString("password", "");
        Set<String> citiesSet = sharedPreferences.getStringSet("cities", new HashSet<>());
        citiesList = new ArrayList<>(citiesSet);
        theme = sharedPreferences.getString("theme", "");
        User user = new User(username, password, citiesList, theme);
        Log.d("user we will process: ", user.toString());

        // for developing map and weather
        username = "Yiming";
        password = "123456";
        citiesList = new ArrayList<>(Arrays.asList("Dallas", "Houston"));
        theme = "Theme 1";


        // set theme
        setAppTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set username to show up on the screen
        TextView textView = findViewById(R.id.textView3);
        textView.setText("Hello, " + username);


        System.out.println(citiesList.size());

        // add cities dynamically
        // BUGFIX
        for (String city : citiesList) {
            if (city.length() == 0) {
                continue;
            }
            addButtonForCity(city);
        }


        // Initializing the UI components
        // The list of locations should be customized per user (change the implementation so that
        // buttons are added to layout programmatically
        Button buttonNew = findViewById(R.id.buttonAddLocation);
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCityDialog();
            }
        });

    }

    //set the theme to the user preference
    private void setAppTheme(String theme) {
        switch (theme) {
            case "Theme 1":
                setTheme(R.style.Theme_MyFirstApp); // Set the default theme
                break;
            case "Theme 2":
                setTheme(R.style.Theme_MySecondApp); // Set the second theme
                break;
            case "Theme 3":
                setTheme(R.style.Theme_MyThirdApp); // Set the third theme
                break;
            default:
                setTheme(R.style.Theme_MyFirstApp); // Set a default theme if the user's theme string is not recognized
        }
    }

    // dialog for user to add city
    private void showAddCityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a New City");

        // Set up the input field
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newCity = input.getText().toString().trim();
                if (!newCity.isEmpty()) {
                    // Add the new city to the list
                    citiesList.add(newCity);
                    // Dynamically create a button for the new city
                    addButtonForCity(newCity);
                    // TODO: save user city selection
                    updateCitiesListInContentProvider(username, citiesList);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    //updates list of cities in the content provider
    private void updateCitiesListInContentProvider(String username, List<String> updatedCitiesList) {
        // Define the URI for the user whose cities list needs to be updated
        Uri uri = Uri.parse("content://edu.uiuc.cs427app.provider/users");

        // Create ContentValues to store the updated cities list
        ContentValues values = new ContentValues();
        values.put(UserContract.COLUMN_FAVORED_CITIES, TextUtils.join(",", updatedCitiesList));

        // Define the selection (where clause) to identify the user by username
        String selection = UserContract.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        // Perform the update operation using the content provider
        int rowsUpdated = getContentResolver().update(uri, values, selection, selectionArgs);

        if (rowsUpdated > 0) {
            // Update successful, show a success message (e.g., via Toast)
            Toast.makeText(this, "Cities list updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Update failed, show an error message (e.g., via Toast)
            Toast.makeText(this, "Failed to update cities list", Toast.LENGTH_SHORT).show();
        }
    }

    // adds a button and the UI elements for a city
    private void addButtonForCity(String city) {
        // Create a new LinearLayout to hold the city details
        LinearLayout cityLayout = new LinearLayout(this);
        cityLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Create a TextView to display the city name
        TextView cityTextView = new TextView(this);
        cityTextView.setText(city);
        cityTextView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f // Weight = 1
        ));
        cityLayout.addView(cityTextView);

        // Create a button to show the map for the city
        Button showMapButton = new Button(this);
        showMapButton.setText("Map");
        showMapButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        showMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic to show the map for the selected city
                // Implement this as per your requirements
                showMap(city);
            }
        });
        cityLayout.addView(showMapButton);

        // Create a button to show the weather for the city
        Button showWeatherButton = new Button(this);
        showWeatherButton.setText("Weather");
        showWeatherButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        showWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic to show the map for the selected city
                // Implement this as per your requirements
                showWeather(city);
            }
        });
        cityLayout.addView(showWeatherButton);

        // Create a button to remove the city from the list
        Button removeCityButton = new Button(this);
        removeCityButton.setText("Remove");
        removeCityButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        removeCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic to remove the city from the list
                // Implement this as per your requirements
                removeCity(city);
                updateCitiesListInContentProvider(username, citiesList);
            }
        });
        cityLayout.addView(removeCityButton);

        // Add the custom city view to the buttonLayout
        LinearLayout buttonLayout = findViewById(R.id.cityButtonLayout);
        buttonLayout.addView(cityLayout);
    }


    // removes a city
    private void removeCity(String cityName) {
        // Remove the city from the list of added cities
        citiesList.remove(cityName);

        // Find the city's view and remove it from the UI
        LinearLayout buttonLayout = findViewById(R.id.cityButtonLayout);
        for (int i = 0; i < buttonLayout.getChildCount(); i++) {
            View child = buttonLayout.getChildAt(i);
            if (child instanceof LinearLayout) {
                TextView cityTextView = (TextView) ((LinearLayout) child).getChildAt(0);
                if (cityTextView instanceof TextView) {
                    String displayedCity = cityTextView.getText().toString();
                    if (displayedCity.equals(cityName)) {
                        buttonLayout.removeViewAt(i);
                        break;
                    }
                }
            }
        }
    }


    // starts show map activity for the city
    void showMap(String selectedCity) {
        // Handle click on show map buttons
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("city", selectedCity);
        startActivity(intent);
    }

    // starts show weather activity for teh city
    private void showWeather(String selectedCity) {
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra("city", selectedCity);
        startActivity(intent);
    }

    //invokes a view when clicked
    @Override
    public void onClick(View view) {

    }

    // logs user out and redirects to login page
    public void logoutButtonClicked(View view) {
        // Perform logout operations here (such as clearing session data, logging out the user, etc.)

        // Example: If you want to finish the current activity and go back to the login page
//        finish();
        // Or, if you want to start the login activity explicitly
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

