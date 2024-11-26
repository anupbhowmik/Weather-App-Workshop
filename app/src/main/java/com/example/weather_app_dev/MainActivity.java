package com.example.weather_app_dev;

import static android.widget.Toast.makeText;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather_app_dev.model.WeatherData;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private String API_KEY="1395bcc1916947dea8e80728242411";
    private TextView tempText, windText, tvFeelsLike;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initializeViews();
        fetchWeatherData();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            fetchWeatherData();
            swipeRefreshLayout.setRefreshing(false);
        });

    }

    private void initializeViews() {
        tempText = findViewById(R.id.tv_temp);
        windText = findViewById(R.id.tv_wind);
        tvFeelsLike = findViewById(R.id.tv_feels_like);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
    }

    public void fetchWeatherData() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String location = "London";
        String url = "https://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q=" + location;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject current = response.getJSONObject("current");
                            WeatherData.Current currentWeather = new WeatherData.Current();
                            currentWeather.tempC = current.getDouble("temp_c");
                            currentWeather.windKph = current.getDouble("wind_kph");
                            currentWeather.feelslike = current.getDouble("feelslike_c");
                            currentWeather.condition = current.getJSONObject("condition").getString("text");
                            updateUI(currentWeather);

                        } catch (JSONException e) {
                            showError("Error parsing weather data");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showError("Error loading weather data");
            }
        });

        queue.add(jsonObjectRequest);

    }

    private void updateUI(WeatherData.Current current) {
        tempText.setText(((int) current.tempC) + "°C");
        windText.setText(current.windKph + " km/h");
        tvFeelsLike.setText("Feels like " + ((int) current.feelslike) + "°C");
    }

    private void showError(String message) {
        makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}