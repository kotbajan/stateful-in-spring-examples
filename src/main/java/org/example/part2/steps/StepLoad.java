package org.example.part2.steps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.part2.Ctx;
import org.example.part2.Repo;
import org.example.part2.Step;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StepLoad implements Step {
    private final Repo repo;

    @Override
    public void execute(Ctx ctx) {
        log.debug("load {}", ctx);
        final Integer data = repo.find(ctx.getKey());
        ctx.setData(data);
    }
}
