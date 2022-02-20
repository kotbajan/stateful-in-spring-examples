package org.example.part2.steps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.part2.Ctx;
import org.example.part2.Step;
import org.example.part2.TransformMultiply2;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StepTransformMultiply implements Step {
    private final TransformMultiply2 multiply2;

    @Override
    public void execute(Ctx ctx) {
        log.debug("multiply {}", ctx);
        final Integer oldData = ctx.getData();

        if (ctx.getKey().length() % 2 == 1) {
            ctx.setData(multiply2.transform(oldData));
        }
    }
}
