package com.vitoria.climio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DailyWeatherDTO {

    private List<String> time;

    @JsonProperty("weather_code")
    private List<Integer> weatherCode;

    @JsonProperty("temperature_2m_max")
    private List<Double> temperatureMax;

    @JsonProperty("temperature_2m_min")
    private List<Double> temperatureMin;

    @JsonProperty("wind_speed_10m_max")
    private List<Double> windSpeedMax;

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<Integer> getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(List<Integer> weatherCode) {
        this.weatherCode = weatherCode;
    }

    public List<Double> getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(List<Double> temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public List<Double> getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(List<Double> temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public List<Double> getWindSpeedMax() {
        return windSpeedMax;
    }

    public void setWindSpeedMax(List<Double> windSpeedMax) {
        this.windSpeedMax = windSpeedMax;
    }
}