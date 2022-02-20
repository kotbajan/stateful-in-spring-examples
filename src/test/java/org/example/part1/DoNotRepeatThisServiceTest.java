package org.example.part1;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@Slf4j
@SpringJUnitConfig
@SpringBootTest(classes = {
        DoNotRepeatThisServiceImpl1.class,
//        DoNotRepeatThisServiceImpl2.class,
//        DoNotRepeatThisServiceImpl3.class,
        Repo.class,
        Transformer.class,
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class DoNotRepeatThisServiceTest {

    @Autowired
    DoNotRepeatThisService service;

    @SpyBean
    Transformer transformer;

    @BeforeEach
    void setUp() {
        Mockito.reset(transformer);
    }

    @Test
    public void functionalTest() {
        String key = "1";

        Integer result = service.execute(key);

        assertEquals(calcExpected(key), result);
        if (service instanceof DoNotRepeatThisServiceImpl1 srv) {
            assertEquals(key, srv.getKey());
        }
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

    @Test
    public void countDownTest() {
        CountDownLatch cdl = new CountDownLatch(2);
        Mockito.doAnswer(invocation -> {
            cdl.countDown();
            final boolean await = cdl.await(5, TimeUnit.SECONDS);
            if (!await) {
                throw new RuntimeException("Не дождались 2го потока");
            }
            return invocation.callRealMethod();
        }).when(transformer).transform(any());

        SoftAssertions softAssertions = new SoftAssertions();

        IntStream.rangeClosed(1, 2)
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
        return key.hashCode() + 1;
    }
}