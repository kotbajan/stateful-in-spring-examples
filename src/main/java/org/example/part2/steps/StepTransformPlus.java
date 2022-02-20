package org.example.part2.steps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.part2.Ctx;
import org.example.part2.Step;
import org.example.part2.TransformPlus1;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StepTransformPlus implements Step {
    private final TransformPlus1 plus1;

    @Override
    public void execute(Ctx ctx) {
        log.debug("plus {}", ctx);
        final Integer oldData = ctx.getData();

        if (ctx.getKey().length() < 2) {
            ctx.setData(plus1.transform(oldData));
        }
    }
}
