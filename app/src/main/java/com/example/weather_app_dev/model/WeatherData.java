package com.example.weather_app_dev.model;

public class WeatherData {
    public Current current;

    public static class Current {

        public double tempC;
        public double windKph;
        public double feelslike;
        public String condition;
    }
}