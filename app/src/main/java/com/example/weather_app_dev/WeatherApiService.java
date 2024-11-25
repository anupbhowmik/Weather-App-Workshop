package com.example.weather_app_dev;

import com.example.weather_app_dev.model.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("v1/current.json")
    Call<WeatherData> getCurrentWeather(
            @Query("key") String apiKey,
            @Query("q") String location
    );
}