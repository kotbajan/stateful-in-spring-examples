package org.example.scopes.steps;

import org.example.scopes.action.ActionType;
import org.springframework.stereotype.Component;

@Component
public class CheckHungryStep implements Step {
    @Override
    public void exec(BusinessLogicContext ctx) {
        if (ctx.getPerson().isHungry()) {
            ctx.setPlan(ActionType.EAT);
        }
    }
}
