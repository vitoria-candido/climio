package com.vitoria.climio.service;

import org.springframework.stereotype.Service;

@Service
public class WeatherThemeService {

    public String getWeatherClass(int weatherCode) {
        if (weatherCode == 0 || weatherCode == 1) {
            return "weather-sunny";
        }

        if (weatherCode == 2 || weatherCode == 3) {
            return "weather-cloudy";
        }

        if (weatherCode == 45 || weatherCode == 48) {
            return "weather-fog";
        }

        if ((weatherCode >= 51 && weatherCode <= 67) || (weatherCode >= 80 && weatherCode <= 82)) {
            return "weather-rain";
        }

        if (weatherCode >= 95 && weatherCode <= 99) {
            return "weather-storm";
        }

        if ((weatherCode >= 71 && weatherCode <= 77) || (weatherCode >= 85 && weatherCode <= 86)) {
            return "weather-cold";
        }

        return "weather-default";
    }

    public String getThemeLabel(int weatherCode) {
        return switch (getWeatherClass(weatherCode)) {
            case "weather-sunny" -> "Clima Ensolarado ☀️";
            case "weather-cloudy" -> "Clima Nublado 🌥️";
            case "weather-fog" -> "Clima com Neblina 🌫";
            case "weather-rain" -> "Clima Chuvoso ☔️";
            case "weather-storm" -> "Clima de Tempestade ⛈️";
            case "weather-cold" -> "Clima Frio 🧣";
            default -> "Clima Indisponível";
        };
    }

    public String getVideoFileName(int weatherCode) {
        return switch (getWeatherClass(weatherCode)) {
            case "weather-sunny" -> "sunny.mp4";
            case "weather-cloudy" -> "cloudy.mp4";
            case "weather-fog" -> "cold.mp4";
            case "weather-rain" -> "rain.mp4";
            case "weather-storm" -> "storm.mp4";
            case "weather-cold" -> "cold.mp4";
            default -> "default.mp4";
        };
    }
}