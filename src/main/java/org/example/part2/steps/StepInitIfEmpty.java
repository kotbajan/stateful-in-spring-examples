package org.example.part2.steps;

import lombok.extern.slf4j.Slf4j;
import org.example.part2.Ctx;
import org.example.part2.Step;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StepInitIfEmpty implements Step {
    @Override
    public void execute(Ctx ctx) {
        log.debug("init {}", ctx);
        if (ctx.getData() == null) {
            ctx.setData(0);
        }
    }
}
