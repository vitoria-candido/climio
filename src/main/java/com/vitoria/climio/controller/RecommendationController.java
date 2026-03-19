package com.vitoria.climio.controller;

import com.vitoria.climio.dto.SearchFormDTO;
import com.vitoria.climio.dto.WeatherResponseDTO;
import com.vitoria.climio.dto.geocoding.NominatimLocationDTO;
import com.vitoria.climio.service.LocationService;
import com.vitoria.climio.service.RecommendationService;
import com.vitoria.climio.service.TextFormatterService;
import com.vitoria.climio.service.WeatherDescriptionService;
import com.vitoria.climio.service.WeatherService;
import com.vitoria.climio.service.WeatherThemeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.format.DateTimeFormatter;

@Controller
public class RecommendationController {

    private final LocationService locationService;
    private final WeatherService weatherService;
    private final RecommendationService recommendationService;
    private final WeatherDescriptionService weatherDescriptionService;
    private final TextFormatterService textFormatterService;
    private final WeatherThemeService weatherThemeService;

    public RecommendationController(LocationService locationService,
                                    WeatherService weatherService,
                                    RecommendationService recommendationService,
                                    WeatherDescriptionService weatherDescriptionService,
                                    TextFormatterService textFormatterService,
                                    WeatherThemeService weatherThemeService) {
        this.locationService = locationService;
        this.weatherService = weatherService;
        this.recommendationService = recommendationService;
        this.weatherDescriptionService = weatherDescriptionService;
        this.textFormatterService = textFormatterService;
        this.weatherThemeService = weatherThemeService;
    }

    @PostMapping("/recommendation")
    public String recommendation(@ModelAttribute SearchFormDTO searchForm, Model model) {

        String formattedCity = textFormatterService.formatCityName(searchForm.getCity());
        String formattedActivity = textFormatterService.formatActivity(searchForm.getActivityType());
        String formattedDate = searchForm.getDate()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        model.addAttribute("city", formattedCity);
        model.addAttribute("date", formattedDate);
        model.addAttribute("activityType", formattedActivity);

        NominatimLocationDTO location = locationService.findFirstLocation(searchForm.getCity());

        if (location == null) {
            model.addAttribute("locationError", "Cidade não encontrada. Tente pesquisar com outro nome.");
            model.addAttribute("weatherClass", "weather-default");
            model.addAttribute("themeLabel", "Clima Indisponível");
            model.addAttribute("videoFileName", "default.mp4");
            return "result";
        }

        String locationName = locationService.buildFriendlyLocationName(location);
        if (locationName == null || locationName.isBlank()) {
            locationName = formattedCity;
        }

        model.addAttribute("locationName", locationName);
        model.addAttribute("latitude", location.getLat());
        model.addAttribute("longitude", location.getLon());

        double latitude = Double.parseDouble(location.getLat());
        double longitude = Double.parseDouble(location.getLon());

        WeatherResponseDTO weather = weatherService.getWeatherForecast(latitude, longitude);

        if (weather == null || weather.getDaily() == null || weather.getDaily().getTime() == null) {
            model.addAttribute("weatherError", "Não foi possível obter a previsão do clima.");
            model.addAttribute("weatherClass", "weather-default");
            model.addAttribute("themeLabel", "Clima Indisponível");
            model.addAttribute("videoFileName", "default.mp4");
            return "result";
        }

        String selectedDate = searchForm.getDate().toString();
        int index = weather.getDaily().getTime().indexOf(selectedDate);

        if (index == -1) {
            model.addAttribute("weatherError", "Data fora do intervalo de previsão.");
            model.addAttribute("weatherClass", "weather-default");
            model.addAttribute("themeLabel", "Clima Indisponível");
            model.addAttribute("videoFileName", "default.mp4");
            return "result";
        }

        double temperatureMax = weather.getDaily().getTemperatureMax().get(index);
        double temperatureMin = weather.getDaily().getTemperatureMin().get(index);
        int weatherCode = weather.getDaily().getWeatherCode().get(index);

        double averageTemperature = (temperatureMax + temperatureMin) / 2.0;

        String recommendation = recommendationService.generateRecommendation(
                searchForm.getActivityType(),
                averageTemperature,
                weatherCode,
                0
        );

        String weatherDescription = weatherDescriptionService.getDescription(weatherCode);
        String weatherEmoji = weatherDescriptionService.getEmoji(weatherCode);
        String extraTip = recommendationService.generateExtraTip(
                searchForm.getActivityType(),
                averageTemperature,
                weatherCode
        );

        String weatherClass = weatherThemeService.getWeatherClass(weatherCode);
        String themeLabel = weatherThemeService.getThemeLabel(weatherCode);
        String videoFileName = weatherThemeService.getVideoFileName(weatherCode);

        model.addAttribute("temperatureMax", String.format("%.1f", temperatureMax));
        model.addAttribute("temperatureMin", String.format("%.1f", temperatureMin));
        model.addAttribute("weatherDescription", weatherDescription);
        model.addAttribute("weatherEmoji", weatherEmoji);
        model.addAttribute("recommendation", recommendation);
        model.addAttribute("extraTip", extraTip);
        model.addAttribute("weatherClass", weatherClass);
        model.addAttribute("themeLabel", themeLabel);
        model.addAttribute("videoFileName", videoFileName);

        return "result";
    }
}