package org.example.scopes.steps;

import org.example.scopes.action.ActionType;
import org.example.scopes.other.LazyObjectSupplierLoader;
import org.example.scopes.wear.ClothesStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "thread", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WearCalculator extends LazyObjectSupplierLoader<ClothesStyle> {
    @Autowired
    PlanCalculator planCalculator;

    @Autowired
    LoadWeatherStep loadWeatherStep;

    @Override
    @NonNull
    public ClothesStyle get() {
        return super.get();
    }

    @Override
    @NonNull
    protected ClothesStyle load() {
        if (ActionType.FILM.equals(planCalculator.get())) {
            return ClothesStyle.UNDERWEAR;
        }

        if (ActionType.PROMENADE.equals(planCalculator.get()) && loadWeatherStep.get().isRain()) {
            return ClothesStyle.RAINCOAT;
        }

        return ClothesStyle.SHORTS;
    }
}
