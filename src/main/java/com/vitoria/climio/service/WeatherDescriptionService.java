package com.vitoria.climio.service;

import org.springframework.stereotype.Service;

@Service
public class WeatherDescriptionService {

    public String getDescription(int weatherCode) {
        switch (weatherCode) {
            case 0:
                return "Céu limpo";
            case 1:
                return "Predominantemente limpo";
            case 2:
                return "Parcialmente nublado";
            case 3:
                return "Nublado";
            case 45:
            case 48:
                return "Neblina";
            case 51:
            case 53:
            case 55:
                return "Garoa";
            case 56:
            case 57:
                return "Garoa congelante";
            case 61:
            case 63:
            case 65:
                return "Chuva";
            case 66:
            case 67:
                return "Chuva congelante";
            case 71:
            case 73:
            case 75:
                return "Neve";
            case 77:
                return "Grãos de neve";
            case 80:
            case 81:
            case 82:
                return "Pancadas de chuva";
            case 85:
            case 86:
                return "Pancadas de neve";
            case 95:
                return "Tempestade";
            case 96:
            case 99:
                return "Tempestade com granizo";
            default:
                return "Condição climática desconhecida";
        }
    }

    public String getEmoji(int weatherCode) {
        switch (weatherCode) {
            case 0:
            case 1:
                return "☀️";
            case 2:
            case 3:
                return "☁️";
            case 45:
            case 48:
                return "🌫️";
            case 51:
            case 53:
            case 55:
            case 61:
            case 63:
            case 65:
            case 80:
            case 81:
            case 82:
                return "🌧️";
            case 71:
            case 73:
            case 75:
            case 77:
            case 85:
            case 86:
                return "❄️";
            case 95:
            case 96:
            case 99:
                return "⛈️";
            default:
                return "🌤️";
        }
    }
}