package org.example.scopes;

import lombok.ToString;
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
@Scope(scopeName = "prototype", proxyMode = ScopedProxyMode.NO)
@ToString
public class BusinessLogic {
    @Autowired @ToString.Exclude WeatherService weatherService;
    @Autowired @ToString.Exclude WardrobeService wardrobeService;

    private Person person;
    private Weather weather;
    private ClothesStyle clothes;
    private boolean useUmbrella;

    public ClothingSet whatShouldIDo(Person person) {
        this.person = person;

        loadWeather();
        calculate();
        takeClothes();

        return new ClothingSet(clothes, useUmbrella);
    }

    private void loadWeather() {
        // Определить погоду
        weather = weatherService.getWeather(person.getCoord());
    }

    private void calculate() {
        clothes = ClothesStyle.SHORTS;
        useUmbrella = false;

        // В дождь надеть плащь и взять зонт
        if (weather.isRain()) {
            clothes = ClothesStyle.RAINCOAT;
            useUmbrella = true;
        }
    }

    private void takeClothes() {
        // Взять выбранное из шкафа
        wardrobeService.takeClothes(person, clothes, useUmbrella);
    }
}
