package com.vitoria.climio.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TextFormatterService {

    private static final Set<String> LOWERCASE_WORDS = Set.of(
            "de", "da", "do", "das", "dos", "e"
    );

    public String formatCityName(String city) {
        if (city == null || city.isBlank()) {
            return "";
        }

        String cleaned = city.trim().replaceAll("\\s+", " ");

        return Arrays.stream(cleaned.split(" "))
                .map(this::formatCityWord)
                .collect(Collectors.joining(" "));
    }

    public String formatActivity(String activityType) {
        if (activityType == null || activityType.isBlank()) {
            return "";
        }

        String cleaned = activityType.trim()
                .replace("_", " ")
                .replaceAll("\\s+", " ");

        return Arrays.stream(cleaned.split(" "))
                .map(this::capitalize)
                .collect(Collectors.joining(" "));
    }

    private String formatCityWord(String word) {
        String normalized = word.toLowerCase(new Locale("pt", "BR"));

        if (LOWERCASE_WORDS.contains(normalized)) {
            return normalized;
        }

        return capitalize(normalized);
    }

    private String capitalize(String word) {
        if (word == null || word.isBlank()) {
            return "";
        }

        String normalized = word.toLowerCase(new Locale("pt", "BR"));
        return normalized.substring(0, 1).toUpperCase(new Locale("pt", "BR")) + normalized.substring(1);
    }
}