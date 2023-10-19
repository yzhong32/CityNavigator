package edu.uiuc.cs427app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import edu.uiuc.cs427app.databinding.ActivityMainBinding;

import android.widget.Button;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    String username;
    String password;
    List<String> citiesList;
    String theme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // set username to show up on the screen
        TextView textView = findViewById(R.id.textView3);
        textView.setText("Hello, " + username);


        // add cities dynamically
        for (String city : citiesList) {
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
        showMapButton.setText("Show Map");
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
            }
        });
        cityLayout.addView(removeCityButton);

        // Add the custom city view to the buttonLayout
        LinearLayout buttonLayout = findViewById(R.id.cityButtonLayout);
        buttonLayout.addView(cityLayout);
    }

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

    private void showMap(String selectedCity) {
        // Handle click on city buttons
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("city", selectedCity);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

    }

    public void logoutButtonClicked(View view) {
        // Perform logout operations here (such as clearing session data, logging out the user, etc.)

        // Example: If you want to finish the current activity and go back to the login page
//        finish();
        // Or, if you want to start the login activity explicitly
         Intent intent = new Intent(this, LoginActivity.class);
         startActivity(intent);
    }
}

