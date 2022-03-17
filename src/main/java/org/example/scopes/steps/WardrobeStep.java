package org.example.scopes.steps;

import org.example.scopes.wear.WardrobeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WardrobeStep implements Step {
    @Autowired
    WardrobeService wardrobeService;

    @Override
    public void exec(BusinessLogicContext ctx) {
        wardrobeService.takeClothes(ctx.getPerson(), ctx.getClothes(), ctx.isUseUmbrella());
    }
}
