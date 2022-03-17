package org.example.scopes.wear;

import lombok.extern.slf4j.Slf4j;
import org.example.scopes.model.Person;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WardrobeService {
    public void takeClothes(Person person, ClothesStyle clothes, boolean useUmbrella) {
        log.debug("{} одевается в {} и {} зонт.", person.getName(), clothes.getName(), useUmbrella ? "берет" : "не берет");
    }
}
