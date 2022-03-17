package org.example.scopes;

import org.example.scopes.model.Person;
import org.example.scopes.model.ClothingSet;
import org.example.scopes.wear.ClothesStyle;
import org.example.scopes.wear.WardrobeService;
import org.example.scopes.weather.Weather;
import org.example.scopes.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "singleton", proxyMode = ScopedProxyMode.NO)
public class BusinessLogic {
    @Autowired WeatherService weatherService;
    @Autowired WardrobeService wardrobeService;

    public ClothingSet whatShouldIDo(Person person) {
        // Определить погоду
        final Weather weather = weatherService.getWeather(person.getCoord());

        ClothesStyle clothes = ClothesStyle.SHORTS;
        boolean useUmbrella = false;

        // В дождь надеть плащь и взять зонт
        if (weather.isRain()) {
            clothes = ClothesStyle.RAINCOAT;
            useUmbrella = true;
        }

        // Взять выбранное из шкафа
        wardrobeService.takeClothes(person, clothes, useUmbrella);

        return new ClothingSet(clothes, useUmbrella);
    }
}
