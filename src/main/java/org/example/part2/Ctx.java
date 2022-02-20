package org.example.part2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;

@RequiredArgsConstructor
@Scope(value = "prototype")
@ToString
@Getter
public class Ctx {
    final String key;

    @Setter
    Integer data;
}
