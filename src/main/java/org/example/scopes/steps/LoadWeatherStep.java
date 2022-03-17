package org.example.scopes.steps;

import org.example.scopes.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoadWeatherStep implements Step {
    @Autowired
    WeatherService weatherService;

    @Override
    public void exec(BusinessLogicContext ctx) {
        ctx.setWeather(weatherService.getWeather(ctx.getPerson().getCoord()));
    }
}
