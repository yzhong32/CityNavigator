package edu.uiuc.cs427app;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Toolbar toolbar;

    // API keys
    private static final String GEOCODING_API_KEY = "AIzaSyBt_Q01vX0SxrkN_cVoGnxjlP-Y717Z7LY";

    private static final float ZOOM_LEVEL = 100.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        String cityName = getIntent().getStringExtra("city").toString();
        String welcome = "Welcome to the " + cityName;
        String cityWeatherInfo = "Detailed information about the map of " + cityName;

        // Constructs initial GUI elements
        toolbar = findViewById(R.id.toolbar);
        TextView welcomeMessage = findViewById(R.id.welcomeText);
        TextView cityInfoMessage = findViewById(R.id.cityInfo);

        // Sets click listener to finish activity
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Sets the initial GUI elements
        welcomeMessage.setText(welcome);
        cityInfoMessage.setText(cityWeatherInfo);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Call the function to fetch latitude and longitude for the city name
        fetchCoordinatesAndUpdateMap(cityName);
    }

    private void fetchCoordinatesAndUpdateMap(String cityName) {
        String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + cityName + "&key=" + GEOCODING_API_KEY;
        Log.d("Url: ", apiUrl);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            if (results.length() > 0) {
                                JSONObject location = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                                double latitude = location.getDouble("lat");
                                double longitude = location.getDouble("lng");
                                Log.d("Latitude", String.valueOf(latitude));
                                Log.d("Longitude", String.valueOf(longitude));
                                // Now you have the latitude and longitude for the given city
                                // Update UI or pass these values as needed
                                updateUI(latitude, longitude);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void updateUI(double latitude, double longitude) {
        if (mMap != null) {
            // Add a marker at the given latitude and longitude
            LatLng cityLocation = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions()
                    .position(cityLocation)
                    .title("Marker for City"));

            // Move the camera to the city's location
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLocation, ZOOM_LEVEL));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
