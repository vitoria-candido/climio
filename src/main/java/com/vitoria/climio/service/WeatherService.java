package com.vitoria.climio.service;

import com.vitoria.climio.dto.WeatherResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponseDTO getWeatherForecast(double latitude, double longitude) {
        String url = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&daily=weather_code,temperature_2m_max,temperature_2m_min,wind_speed_10m_max&timezone=auto&forecast_days=16",
                latitude,
                longitude
        );

        return restTemplate.getForObject(url, WeatherResponseDTO.class);
    }
}