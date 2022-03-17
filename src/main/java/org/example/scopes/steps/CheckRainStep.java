package org.example.scopes.steps;

import org.example.scopes.action.ActionType;
import org.example.scopes.wear.ClothesStyle;
import org.springframework.stereotype.Component;

@Component
public class CheckRainStep implements Step {
    @Override
    public void exec(BusinessLogicContext ctx) {
        if (ctx.getPlan() == ActionType.PROMENADE && ctx.getWeather().isRain()) {
            if (ctx.getWeather().isStorm()) {
                ctx.setPlan(ActionType.PANIC);
            } else {
                ctx.setClothes(ClothesStyle.RAINCOAT);
                ctx.setUseUmbrella(true);
            }
        }
    }
}
