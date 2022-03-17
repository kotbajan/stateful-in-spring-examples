package org.example.scopes.model;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Getter
@AllArgsConstructor
@ToString
public class ApiQuestion {
    Person person;
}
