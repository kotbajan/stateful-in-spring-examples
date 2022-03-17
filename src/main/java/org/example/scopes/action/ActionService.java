package org.example.scopes.action;

import lombok.extern.slf4j.Slf4j;
import org.example.scopes.model.Person;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ActionService {
    public void doAction(Person person, ActionType action) {
        log.debug("{} отправляется {}.", person.getName(), action.getName());
    }
}
