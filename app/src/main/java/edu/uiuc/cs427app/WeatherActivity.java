package edu.uiuc.cs427app;

import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView cityNameTextView, dateTimeTextView, temperatureTextView, weatherTextView, humidityTextView, windTextView;


    // called when the activity is started to set layout and content
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // Initializing the GUI elements
        toolbar = findViewById(R.id.toolbar);
        TextView welcomeMessage = findViewById(R.id.welcomeText);
        TextView cityInfoMessage = findViewById(R.id.cityInfo);
        cityNameTextView = findViewById(R.id.cityName);
        dateTimeTextView = findViewById(R.id.dateTime);
        temperatureTextView = findViewById(R.id.temperature);
        weatherTextView = findViewById(R.id.weather);
        humidityTextView = findViewById(R.id.humidity);
        windTextView = findViewById(R.id.wind);


        String cityName = getIntent().getStringExtra("city").toString();
        String welcome = "Welcome to the " + cityName;
        String cityWeatherInfo = "Detailed information about the weather of " + cityName;


        // sets the listener for when activity is finished.
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        welcomeMessage.setText(welcome);
        cityInfoMessage.setText(cityWeatherInfo);


        // Make API call to OpenWeatherMap
        RequestQueue queue = Volley.newRequestQueue(this);
        String apiKey = "903578151031eda4a610912f03a5c9ad";
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + apiKey;
        Log.d("url: ", apiUrl);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String name = response.getString("name");
                            long unixTimestamp = response.getLong("dt");
                            // Convert Unix timestamp to milliseconds
                            long unixTimestampMillis = unixTimestamp * 1000;
                            // Create a Date object from the Unix timestamp
                            Date date = new Date(unixTimestampMillis);
                            // Format the Date object as a readable date and time string
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                            String formattedDateTime = dateFormat.format(date);

                            JSONObject main = response.getJSONObject("main");
                            double temperature = main.getDouble("temp");
                            int humidity = main.getInt("humidity");
                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject weatherObject = weatherArray.getJSONObject(0);
                            String weatherDescription = weatherObject.getString("description");
                            JSONObject wind = response.getJSONObject("wind");
                            double windSpeed = wind.getDouble("speed");

                            // Update TextViews with weather data
                            cityNameTextView.setText("name: " + name);
                            dateTimeTextView.setText("Time: "+String.valueOf(formattedDateTime));
                            temperatureTextView.setText("Temperature: " + String.valueOf(temperature));
                            weatherTextView.setText("Weather: "+ weatherDescription);
                            humidityTextView.setText("Humidity: " + String.valueOf(humidity));
                            windTextView.setText("Wind speed: " + String.valueOf(windSpeed));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> error.printStackTrace());

        queue.add(request);
    }
}
