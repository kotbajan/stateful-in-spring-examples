package org.example.scopes.steps;

import org.example.scopes.model.Person;
import org.example.scopes.other.LazyObjectSupplierLoader;
import org.example.scopes.weather.Weather;
import org.example.scopes.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "thread", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoadWeatherStep extends LazyObjectSupplierLoader<Weather> {
    @Autowired
    WeatherService weatherService;

    @Autowired
    Person person;

    @Override
    @NonNull
    public Weather get() {
        return super.get();
    }

    @Override
    @NonNull
    protected Weather load() {
        return weatherService.getWeather(person.getCoord());
    }
}
