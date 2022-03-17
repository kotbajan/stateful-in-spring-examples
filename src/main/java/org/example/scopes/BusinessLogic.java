package org.example.scopes;

import lombok.ToString;
import org.example.scopes.model.ClothingSet;
import org.example.scopes.model.Person;
import org.example.scopes.steps.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "thread", proxyMode = ScopedProxyMode.TARGET_CLASS)
@ToString
public class BusinessLogic {
    @Autowired LoadWeatherStep loadWeatherStep;
    @Autowired CheckHungryStep checkHungryStep;
    @Autowired CheckRainStep checkRainStep;
    @Autowired CheckColdStep checkColdStep;
    @Autowired WardrobeStep wardrobeStep;
    @Autowired ActionStep actionStep;

    public ClothingSet whatShouldIDo(Person person) {
        BusinessLogicContext ctx = new BusinessLogicContext(person);

        loadWeatherStep.exec(ctx);
        checkHungryStep.exec(ctx);
        checkRainStep.exec(ctx);
        checkColdStep.exec(ctx);
        wardrobeStep.exec(ctx);
        actionStep.exec(ctx);

        return new ClothingSet(ctx.getClothes(), ctx.isUseUmbrella());
    }
}
