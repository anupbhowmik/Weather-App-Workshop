package com.example.weather_app_dev;

import static android.widget.Toast.makeText;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.weather_app_dev.model.WeatherData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private String API_KEY="1395bcc1916947dea8e80728242411";
    private TextView tempText, windText;
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
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
    }


    private void fetchWeatherData() {

        // You can replace "London" with any location or get it from user input
        RetrofitClient.getInstance()
                .getApiService()
                .getCurrentWeather(API_KEY, "London")
                .enqueue(new Callback<WeatherData>() {
                    @Override
                    public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            WeatherData.Current current = response.body().current;
                            updateUI(current);
                        } else {
                            showError("Error: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherData> call, Throwable t) {
                        showError("Network Error: " + t.getMessage());
                    }
                });
    }

    private void updateUI(WeatherData.Current current) {
        tempText.setText(String.format("%.0f°C", current.tempC));
        windText.setText(String.format("%.1f km/h", current.windKph));
    }

    private void showError(String message) {
        makeText(this, "error", Toast.LENGTH_SHORT).show();
    }
}