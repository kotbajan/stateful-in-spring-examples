package org.example.scopes.model;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.example.scopes.wear.ClothesStyle;

@Jacksonized
@Builder
@Getter
@AllArgsConstructor
@ToString
public class ClothingSet {
    ClothesStyle clothes;
    boolean umbrella;
}
