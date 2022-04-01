package org.example.scopes.steps;

import org.example.scopes.action.ActionService;
import org.example.scopes.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "thread", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ActionStep {
    @Autowired
    ActionService actionService;

    @Autowired
    Person person;

    @Autowired
    PlanCalculator planCalculator;

    public void exec() {
        actionService.doAction(person, planCalculator.get());
    }
}
