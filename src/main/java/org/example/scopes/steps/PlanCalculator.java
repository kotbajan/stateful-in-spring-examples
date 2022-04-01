package org.example.scopes.steps;

import org.example.scopes.action.ActionType;
import org.example.scopes.model.Person;
import org.example.scopes.other.LazyObjectSupplierLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "thread", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PlanCalculator extends LazyObjectSupplierLoader<ActionType> {
    @Autowired
    Person person;

    @Autowired
    LoadWeatherStep loadWeatherStep;

    @Override
    @NonNull
    public ActionType get() {
        return super.get();
    }

    @Override
    @NonNull
    protected ActionType load() {
        if (person.isHungry()) {
            return ActionType.EAT;
        }
        if (loadWeatherStep.get().isRain()) {
            if (loadWeatherStep.get().isStorm()) {
                return ActionType.PANIC;
            }
        }
        return ActionType.PROMENADE;
    }
}
