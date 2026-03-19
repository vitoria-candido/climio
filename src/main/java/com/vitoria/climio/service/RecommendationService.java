package com.vitoria.climio.service;

import org.springframework.stereotype.Service;

@Service
public class RecommendationService {

    public String generateRecommendation(String activityType, double temperature, int weatherCode, double windSpeed) {
        String activity = activityType.toUpperCase();

        boolean isBadWeather = isRainy(weatherCode) || windSpeed > 25;
        boolean isCold = temperature < 18;
        boolean isHot = temperature > 30;
        boolean isMild = temperature >= 18 && temperature <= 30;

        switch (activity) {
            case "CORRIDA":
            case "CAMINHADA":
                if (isRainy(weatherCode)) {
                    return "Não é o melhor clima para " + activity.toLowerCase() + ": há chance de chuva ou tempo ruim.";
                }
                if (windSpeed > 25) {
                    return "O vento está forte para " + activity.toLowerCase() + ". Vá com atenção.";
                }
                if (isHot) {
                    return "O clima está quente para " + activity.toLowerCase() + ".";
                }
                if (isCold) {
                    return "O clima está frio, mas ainda pode ser bom para " + activity.toLowerCase() + " com roupa adequada.";
                }
                if (isMild) {
                    return "Ótimo clima para " + activity.toLowerCase() + ".";
                }
                break;

            case "PRAIA":
                if (isRainy(weatherCode)) {
                    return "Não é um bom momento para praia por causa do tempo chuvoso.";
                }
                if (isCold) {
                    return "Pode não ser ideal para praia, porque a temperatura está baixa.";
                }
                if (windSpeed > 25) {
                    return "A praia pode não ser tão agradável agora por causa do vento forte.";
                }
                if (isHot || isMild) {
                    return "Bom clima para praia.";
                }
                break;

            case "TRILHA":
                if (isBadWeather) {
                    return "Não é recomendável fazer trilha agora por causa das condições climáticas.";
                }
                if (isHot) {
                    return "A trilha é possível, mas o calor pede atenção com hidratação.";
                }
                return "Condições favoráveis para trilha.";

            case "BIKE":
                if (isRainy(weatherCode)) {
                    return "Pedalar com esse clima pode ser arriscado por causa da chuva.";
                }
                if (windSpeed > 25) {
                    return "O vento forte pode atrapalhar bastante o passeio de bike.";
                }
                if (isHot) {
                    return "Bom para bike, mas evite horários muito quentes.";
                }
                return "Bom clima para andar de bike.";

            default:
                if (isRainy(weatherCode)) {
                    return "O clima não parece muito favorável para atividades ao ar livre.";
                }
                if (isHot) {
                    return "O clima está quente. Planeje sua atividade com hidratação.";
                }
                if (isCold) {
                    return "O clima está frio. Considere roupas adequadas.";
                }
                return "O clima parece favorável para sua atividade.";
        }

        return "Não foi possível gerar uma recomendação no momento.";
    }

    public String generateExtraTip(String activityType, double temperature, int weatherCode) {
        if (isRainy(weatherCode)) {
            return "Leve guarda-chuva ou capa de chuva.";
        }

        if (temperature > 30) {
            return "Leve água, use protetor solar e prefira horários mais frescos.";
        }

        if (temperature < 18) {
            return "Use roupas adequadas para o frio.";
        }

        if ("PRAIA".equalsIgnoreCase(activityType)) {
            return "Não esqueça do protetor solar e de se hidratar.";
        }

        if ("TRILHA".equalsIgnoreCase(activityType) || "CAMINHADA".equalsIgnoreCase(activityType)) {
            return "Use calçado confortável e leve água.";
        }

        if ("CORRIDA".equalsIgnoreCase(activityType) || "BIKE".equalsIgnoreCase(activityType)) {
            return "Faça alongamento e leve água para o percurso.";
        }

        return "Planeje sua atividade com conforto e segurança.";
    }

    private boolean isRainy(int weatherCode) {
        return weatherCode == 51 || weatherCode == 53 || weatherCode == 55
                || weatherCode == 61 || weatherCode == 63 || weatherCode == 65
                || weatherCode == 80 || weatherCode == 81 || weatherCode == 82
                || weatherCode == 95 || weatherCode == 96 || weatherCode == 99;
    }
}