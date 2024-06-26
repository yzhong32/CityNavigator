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

    // GUI elements
    private Toolbar toolbar;
    private TextView cityNameTextView, dateTimeTextView, temperatureTextView, weatherTextView, humidityTextView, windTextView;


    /**
     * Called when the activity is started to set layout and content.
     *
     * @param savedInstanceState The saved state of the activity.
     */
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

        // Retrieve city name from the intent
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

        // Set welcome messages
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
                            double temperature = main.getDouble("temp") - 273;
                            int humidity = main.getInt("humidity");
                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject weatherObject = weatherArray.getJSONObject(0);
                            String weatherDescription = weatherObject.getString("description");
                            JSONObject wind = response.getJSONObject("wind");
                            double windSpeed = wind.getDouble("speed");
                            double degree = wind.getDouble("deg");
//{"speed":8.75,"deg":180,"gust":12.35}


                            // Update TextViews with weather data
                            cityNameTextView.setText("name: " + name);
                            dateTimeTextView.setText("Time: "+String.valueOf(formattedDateTime));
                            temperatureTextView.setText("Temperature: " + String.valueOf((int) Math.round(temperature)) + "\u00B0 C");
                            weatherTextView.setText("Weather: "+ weatherDescription);
                            humidityTextView.setText("Humidity: " + String.valueOf(humidity) + "%");
                            windTextView.setText("Wind speed: " + String.valueOf(windSpeed) + " km/h" + "\n" + "\n" + "\n" +"Wind deg: " + String.valueOf(degree) +  "\u00B0");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> error.printStackTrace());

        queue.add(request);
    }
}
