package com.example.weather_app_dev.model;

import com.google.gson.annotations.SerializedName;

public class WeatherData {
    public Current current;

    public static class Current {
        @SerializedName("temp_c")
        public double tempC;

        @SerializedName("wind_kph")
        public double windKph;

        @SerializedName("precip_mm")
        public double precipMm;

        @SerializedName("feelslike")
        public  double feelslike;
    }
}