package org.example.scopes.weather;

import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    public Weather getWeather(String gps) {
        return Weather.builder()
                .cold(gps.contains("Moscow"))
                .rain(gps.contains("Petersburg"))
                .build();
    }
}
