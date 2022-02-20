package org.example.part2;

import lombok.extern.slf4j.Slf4j;
import org.example.part2.steps.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class DoNotRepeatThisServiceImpl4 implements DoNotRepeatThisService {
    private final ObjectProvider<Ctx> contextProvider;
    private final List<Step> steps;

    public DoNotRepeatThisServiceImpl4(
            ObjectProvider<Ctx> contextProvider,
            StepLoad load,
            StepInitIfEmpty init,
            StepTransformPlus transformPlus,
            StepTransformMultiply transformMultiply,
            StepSave save,
            StepNotify notify
    ) {
        this.contextProvider = contextProvider;
        this.steps = Arrays.asList(
                load,
                init,
                transformPlus,
                transformMultiply,
                save,
                notify
        );
    }

    public Integer execute(String key) {
        final Ctx ctx = contextProvider.getObject(key);

        steps.forEach(it -> it.execute(ctx));

        return ctx.getData();
    }
}
