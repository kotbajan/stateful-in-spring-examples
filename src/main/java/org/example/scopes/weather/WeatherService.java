package org.example.scopes.weather;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WeatherService {
    @Autowired
    @Qualifier("correlationId")
    ObjectProvider<String> correlationId;

    public Weather getWeather(String gps) {
        log.info("Coord: {}, correlationId: {}", gps, correlationId.getObject());
        return Weather.builder()
                .cold(gps.contains("Moscow"))
                .rain(gps.contains("Petersburg"))
                .build();
    }
}
