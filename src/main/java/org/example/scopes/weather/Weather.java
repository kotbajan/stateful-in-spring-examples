package org.example.scopes.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class Weather {
    boolean rain;
    boolean storm;
    boolean cold;
}
