package org.example.part2.steps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.part2.Ctx;
import org.example.part2.NotifyService;
import org.example.part2.Repo;
import org.example.part2.Step;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StepNotify implements Step {
    private final NotifyService notifyService;

    @Override
    public void execute(Ctx ctx) {
        log.debug("notify {}", ctx);
        notifyService.notify(ctx.getKey(), ctx.getData());
    }
}
