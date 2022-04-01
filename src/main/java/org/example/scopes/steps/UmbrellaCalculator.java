package org.example.scopes.steps;

import org.example.scopes.other.LazyObjectSupplierLoader;
import org.example.scopes.weather.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "thread", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UmbrellaCalculator extends LazyObjectSupplierLoader<Boolean> {
    @Autowired
    LoadWeatherStep loadWeatherStep;

    @Override
    @NonNull
    public Boolean get() {
        return super.get();
    }

    @Override
    @NonNull
    protected Boolean load() {
        final Weather weather = loadWeatherStep.get();
        return weather.isRain();
    }
}
