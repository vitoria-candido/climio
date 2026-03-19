package com.vitoria.climio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrentWeatherDTO {

    @JsonProperty("temperature_2m")
    private double temperature;

    @JsonProperty("weather_code")
    private int weatherCode;

    @JsonProperty("wind_speed_10m")
    private double windSpeed;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(int weatherCode) {
        this.weatherCode = weatherCode;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
}