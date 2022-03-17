package org.example.scopes.action;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActionType {
    PANIC("паниковать"),
    PROMENADE("прогуляться"),
    FILM("посмотреть фильм"),
    EAT("поесть"),
    ;
    final String name;
}
