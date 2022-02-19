package org.example.part1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Transformer {
    @Autowired
    ApplicationContext context;

    public Integer transform(Integer value) {
        final Integer newValue = value + 1;
        final DoNotRepeatThisService service = context.getBean(DoNotRepeatThisService.class);
        if (service instanceof DoNotRepeatThisServiceImpl1 srv) {
            log.debug("transform {}, {} to {}", srv.getKey(), value, newValue);
        } else {
            log.debug("transform {} to {}", value, newValue);
        }
        return newValue;
    }
}
