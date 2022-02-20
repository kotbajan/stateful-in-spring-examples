package org.example.part2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransformPlus1 {
    public Integer transform(Integer value) {
        final Integer newValue = value + 1;
        log.debug("transform {} to {}", value, newValue);
        return newValue;
    }
}
