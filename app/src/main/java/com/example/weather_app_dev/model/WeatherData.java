package com.example.weather_app_dev.model;

import com.google.gson.annotations.SerializedName;

public class WeatherData {
    public Current current;

    public static class Current {

        public double tempC;

        public double windKph;

        public double precipMm;

        public double feelslike;
        public String condition;
    }
}