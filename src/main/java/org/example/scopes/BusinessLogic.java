package org.example.scopes;

import lombok.ToString;
import org.example.scopes.model.ClothingSet;
import org.example.scopes.model.Person;
import org.example.scopes.steps.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "thread", proxyMode = ScopedProxyMode.TARGET_CLASS)
@ToString
public class BusinessLogic {
    @Autowired UmbrellaCalculator umbrellaCalculator;
    @Autowired WearCalculator wearCalculator;
    @Autowired WardrobeStep wardrobeStep;
    @Autowired ActionStep actionStep;
    @Autowired
    ObjectProvider<Person> personFactory;

    public ClothingSet whatShouldIDo(Person person) {
        personFactory.getObject(person);

        wardrobeStep.exec();
        actionStep.exec();

        return new ClothingSet(wearCalculator.get(), umbrellaCalculator.get());
    }
}
