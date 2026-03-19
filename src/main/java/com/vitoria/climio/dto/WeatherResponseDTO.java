package com.vitoria.climio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherResponseDTO {

    private double latitude;
    private double longitude;

    @JsonProperty("current")
    private CurrentWeatherDTO current;

    @JsonProperty("daily")
    private DailyWeatherDTO daily;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public CurrentWeatherDTO getCurrent() {
        return current;
    }

    public void setCurrent(CurrentWeatherDTO current) {
        this.current = current;
    }

    public DailyWeatherDTO getDaily() {
        return daily;
    }

    public void setDaily(DailyWeatherDTO daily) {
        this.daily = daily;
    }
}