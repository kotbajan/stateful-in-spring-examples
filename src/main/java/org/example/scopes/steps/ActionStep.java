package org.example.scopes.steps;

import org.example.scopes.action.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActionStep implements Step {
    @Autowired
    ActionService actionService;

    @Override
    public void exec(BusinessLogicContext ctx) {
        actionService.doAction(ctx.getPerson(), ctx.getPlan());
    }
}
