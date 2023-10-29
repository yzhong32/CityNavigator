package edu.uiuc.cs427app;

import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class WeatherActivity extends AppCompatActivity {
    private Toolbar toolbar;

    // called when the activity is started to set layout and content
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        String cityName = getIntent().getStringExtra("city").toString();
        String welcome = "Welcome to the " + cityName;
        String cityWeatherInfo = "Detailed information about the weather of " + cityName;

        // Initializing the GUI elements
        toolbar = findViewById(R.id.toolbar);
        TextView welcomeMessage = findViewById(R.id.welcomeText);
        TextView cityInfoMessage = findViewById(R.id.cityInfo);

        // sets the listener for when activity is finished. 
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        welcomeMessage.setText(welcome);
        cityInfoMessage.setText(cityWeatherInfo);
    }
}