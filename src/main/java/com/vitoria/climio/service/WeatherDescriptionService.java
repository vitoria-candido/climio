package com.vitoria.climio.service;

import org.springframework.stereotype.Service;

@Service
public class WeatherDescriptionService {

    public String getDescription(int weatherCode) {
        return switch (weatherCode) {
            case 0 -> "Céu limpo";
            case 1 -> "Predominantemente limpo";
            case 2 -> "Parcialmente nublado";
            case 3 -> "Nublado";
            case 45, 48 -> "Neblina";
            case 51, 53, 55 -> "Garoa";
            case 56, 57 -> "Garoa congelante";
            case 61, 63, 65 -> "Chuva";
            case 66, 67 -> "Chuva congelante";
            case 71, 73, 75 -> "Neve";
            case 77 -> "Grãos de neve";
            case 80, 81, 82 -> "Pancadas de chuva";
            case 85, 86 -> "Pancadas de neve";
            case 95 -> "Tempestade";
            case 96, 99 -> "Tempestade com granizo";
            default -> "Condição climática desconhecida";
        };
    }

    public String getEmoji(int weatherCode) {
        return switch (weatherCode) {
            case 0, 1 -> "☀️";
            case 2, 3 -> "☁️";
            case 45, 48 -> "🌫️";
            case 51, 53, 55, 61, 63, 65, 80, 81, 82 -> "🌧️";
            case 56, 57, 66, 67 -> "🌧️";
            case 71, 73, 75, 77, 85, 86 -> "❄️";
            case 95, 96, 99 -> "⛈️";
            default -> "🌤️";
        };
    }
}