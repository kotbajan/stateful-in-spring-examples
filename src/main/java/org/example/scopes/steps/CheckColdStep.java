package org.example.scopes.steps;

import org.example.scopes.action.ActionType;
import org.example.scopes.wear.ClothesStyle;
import org.springframework.stereotype.Component;

@Component
public class CheckColdStep implements Step {
    @Override
    public void exec(BusinessLogicContext ctx) {
        if (ctx.getPlan() == ActionType.PROMENADE && ctx.getWeather().isCold()) {
            ctx.setPlan(ActionType.FILM);
            ctx.setClothes(ClothesStyle.UNDERWEAR);
        }
    }
}
