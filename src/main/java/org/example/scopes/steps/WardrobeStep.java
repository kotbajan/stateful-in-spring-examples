package org.example.scopes.steps;

import org.example.scopes.model.Person;
import org.example.scopes.wear.WardrobeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "thread", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WardrobeStep {
    @Autowired
    Person person;

    @Autowired
    WearCalculator wearCalculator;

    @Autowired
    UmbrellaCalculator umbrellaCalculator;

    @Autowired
    WardrobeService wardrobeService;

    public void exec() {
        wardrobeService.takeClothes(person, wearCalculator.get(), umbrellaCalculator.get());
    }
}
