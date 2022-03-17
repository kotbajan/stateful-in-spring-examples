package org.example.scopes.steps;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.scopes.action.ActionType;
import org.example.scopes.model.Person;
import org.example.scopes.wear.ClothesStyle;
import org.example.scopes.weather.Weather;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
@RequiredArgsConstructor
public class BusinessLogicContext {
    private final Person person;
    private Weather weather;
    private ClothesStyle clothes = ClothesStyle.SHORTS;
    private boolean useUmbrella = false;
    private ActionType plan = ActionType.PROMENADE;
}
