package org.example.scopes.wear;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClothesStyle {
    SHORTS("шорты"),
    RAINCOAT("плащ дождевик"),
    UNDERWEAR("самый минимум"),
    ;

    final String name;
}

