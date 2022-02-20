package org.example.part2;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.example.part2.steps.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringJUnitConfig
@SpringBootTest(classes = {
        DoNotRepeatThisServiceImpl4.class,
        Repo.class,
        TransformPlus1.class,
        TransformMultiply2.class,
        Ctx.class,
        StepInitIfEmpty.class,
        StepLoad.class,
        StepSave.class,
        StepNotify.class,
        StepTransformMultiply.class,
        StepTransformPlus.class,
        NotifyService.class,
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class DoNotRepeatThisServiceTest {

    @Autowired
    DoNotRepeatThisService service;

    @Test
    public void functionalTest() {
        String key = "1";

        Integer result = service.execute(key);

        assertEquals(calcExpected(key), result);
    }

    @Test
    public void stressTest() {
        Instant start = Instant.now();

        IntStream.rangeClosed(1, 1000)
                .boxed()
                .map(Object::toString)
                .parallel()
                .forEach(it -> {
                    Integer result = service.execute(it);
                    assertNotNull(result);
                });

        Instant stop = Instant.now();
        Duration duration = Duration.between(start, stop);
        Duration expected = Duration.ofSeconds(5);
        Assertions.assertThat(duration)
                .isLessThan(expected);
    }

    @Test
    public void stressWithCheckTest() {
        SoftAssertions softAssertions = new SoftAssertions();

        IntStream.rangeClosed(1, 1000)
                .boxed()
                .map(Object::toString)
                .parallel()
                .forEach(it -> {
                    Integer result = service.execute(it);
                    Integer expected = calcExpected(it);
                    softAssertions.assertThat(result)
                            .isEqualTo(expected);
                });

        softAssertions.assertAll();
    }

    private Integer calcExpected(String key) {
        int data = key.hashCode();
        if (key.length() < 2) {
            data += 1;
        }
        if (key.length() % 2 == 1) {
            data *= 2;
        }
        return data;
    }
}