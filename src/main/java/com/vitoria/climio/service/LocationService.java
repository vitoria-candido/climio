package com.vitoria.climio.service;

import com.vitoria.climio.dto.geocoding.NominatimLocationDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class LocationService {

    private final RestTemplate restTemplate;

    public LocationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public NominatimLocationDTO findFirstLocation(String city) {
        if (city == null || city.isBlank()) {
            return null;
        }

        String originalCity = city.trim().replaceAll("\\s+", " ");
        String normalizedInput = normalizeText(originalCity);

        NominatimLocationDTO result = searchBestLocation(originalCity, normalizedInput);
        if (result != null) {
            return result;
        }

        String noAccentCity = removeAccents(originalCity);
        if (!noAccentCity.equalsIgnoreCase(originalCity)) {
            result = searchBestLocation(noAccentCity, normalizedInput);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    public String buildFriendlyLocationName(NominatimLocationDTO location) {
        if (location == null) {
            return "";
        }

        String city = firstNonBlank(
                location.getCity(),
                location.getTown(),
                location.getVillage(),
                location.getMunicipality(),
                location.getCounty(),
                location.getStateDistrict(),
                location.getName()
        );

        String state = firstNonBlank(
                location.getState(),
                location.getRegion()
        );

        String country = location.getCountry();

        if (isBlank(city) && isBlank(state) && isBlank(country)) {
            return location.getDisplayName();
        }

        if (!isBlank(city) && !isBlank(state) && !isBlank(country)) {
            return city + ", " + state + ", " + country;
        }

        if (!isBlank(city) && !isBlank(country)) {
            return city + ", " + country;
        }

        if (!isBlank(state) && !isBlank(country)) {
            return state + ", " + country;
        }

        return firstNonBlank(city, state, country, location.getDisplayName());
    }

    private NominatimLocationDTO searchBestLocation(String cityQuery, String normalizedInput) {
        List<NominatimLocationDTO> locations = searchLocations(cityQuery);

        if (locations == null || locations.isEmpty()) {
            return null;
        }

        List<NominatimLocationDTO> ranked = locations.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(location -> scoreLocation(location, normalizedInput)))
                .toList();

        if (ranked.isEmpty()) {
            return null;
        }

        for (NominatimLocationDTO location : ranked) {
            if (isValidMatch(location, normalizedInput)) {
                return location;
            }
        }

        return null;
    }

    private List<NominatimLocationDTO> searchLocations(String cityQuery) {
        String encodedCity = URLEncoder.encode(cityQuery, StandardCharsets.UTF_8);

        String url = "https://nominatim.openstreetmap.org/search?q="
                + encodedCity
                + "&format=json"
                + "&addressdetails=1"
                + "&limit=10"
                + "&accept-language=pt-BR";

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Climio/1.0");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<NominatimLocationDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<NominatimLocationDTO>>() {
                }
        );

        return response.getBody();
    }

    private int scoreLocation(NominatimLocationDTO location, String normalizedInput) {
        String name = normalizeText(location.getName());
        String city = normalizeText(location.getCity());
        String town = normalizeText(location.getTown());
        String village = normalizeText(location.getVillage());
        String municipality = normalizeText(location.getMunicipality());
        String displayName = normalizeText(location.getDisplayName());
        String type = normalizeText(location.getType());

        int score = 1000;

        if (name.equals(normalizedInput)) {
            score -= 500;
        }

        if (city.equals(normalizedInput)
                || town.equals(normalizedInput)
                || village.equals(normalizedInput)
                || municipality.equals(normalizedInput)) {
            score -= 450;
        }

        if (displayName.startsWith(normalizedInput + ",")) {
            score -= 250;
        } else if (displayName.contains(normalizedInput)) {
            score -= 120;
        }

        if ("city".equals(type)
                || "town".equals(type)
                || "municipality".equals(type)
                || "administrative".equals(type)
                || "village".equals(type)) {
            score -= 80;
        }

        return score;
    }

    private boolean isValidMatch(NominatimLocationDTO location, String normalizedInput) {
        String mainName = normalizeText(firstNonBlank(
                location.getCity(),
                location.getTown(),
                location.getVillage(),
                location.getMunicipality(),
                location.getCounty(),
                location.getName()
        ));

        String displayName = normalizeText(location.getDisplayName());

        if (mainName.isBlank() && displayName.isBlank()) {
            return false;
        }

        if (mainName.equals(normalizedInput)) {
            return true;
        }

        if (displayName.startsWith(normalizedInput + ",")) {
            return true;
        }

        if (displayName.contains(normalizedInput)) {
            return true;
        }

        if (!mainName.isBlank() && (mainName.startsWith(normalizedInput) || normalizedInput.startsWith(mainName))) {
            return true;
        }

        int distance = levenshteinDistance(normalizedInput, mainName);

        int allowedDistance;
        if (normalizedInput.length() <= 4) {
            allowedDistance = 0;
        } else if (normalizedInput.length() <= 7) {
            allowedDistance = 1;
        } else {
            allowedDistance = 1;
        }

        return distance <= allowedDistance;
    }

    private String firstNonBlank(String... values) {
        for (String value : values) {
            if (!isBlank(value)) {
                return value.trim();
            }
        }
        return "";
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isBlank();
    }

    private String normalizeText(String text) {
        if (text == null) {
            return "";
        }

        return removeAccents(text)
                .toLowerCase(Locale.ROOT)
                .trim()
                .replaceAll("\\s+", " ");
    }

    private String removeAccents(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", "");
    }

    private int levenshteinDistance(String first, String second) {
        if (first == null || second == null) {
            return Integer.MAX_VALUE;
        }

        int[][] dp = new int[first.length() + 1][second.length() + 1];

        for (int i = 0; i <= first.length(); i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= second.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= first.length(); i++) {
            for (int j = 1; j <= second.length(); j++) {
                int cost = first.charAt(i - 1) == second.charAt(j - 1) ? 0 : 1;

                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost
                );
            }
        }

        return dp[first.length()][second.length()];
    }
}