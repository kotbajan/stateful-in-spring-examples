package org.example.scopes;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.example.scopes.action.ActionService;
import org.example.scopes.model.ApiAnswer;
import org.example.scopes.model.ApiQuestion;
import org.example.scopes.model.Person;
import org.example.scopes.wear.WardrobeService;
import org.example.scopes.weather.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = {
                BusinessLogic.class,
                Controller.class,
                FacadeService.class,
                ActionService.class,
                WardrobeService.class,
                WeatherService.class,
                ControllerTest.Config.class,
        }
)
@SpringJUnitConfig
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Slf4j
public class ControllerTest {
    final ObjectMapper objectMapper = new ObjectMapper();
    final String MSK = "Moscow";
    final String SPB = "Saint Petersburg";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    Controller controller;

    @Test
    void functionalTestMsk() {
        final String coord = MSK;
        final ApiAnswer answer = doRequest(coord);
        assertTrue(isValid(coord, answer));
    }

    @Test
    void functionalTestSpb() {
        final String coord = SPB;
        final ApiAnswer answer = doRequest(coord);
        assertTrue(isValid(coord, answer));
    }

    @Test
    public void stressTest() {
        Instant start = Instant.now();

        IntStream.rangeClosed(1, 1000)
                .boxed()
                .map(it -> it % 2 == 0 ? MSK : SPB)
                .parallel()
                .forEach(it -> {
                    ApiAnswer result = doRequest(it);
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
                .map(it -> it % 2 == 0 ? MSK : SPB)
                .parallel()
                .forEach(it -> {
                    ApiAnswer result = doRequest(it);
                    assertNotNull(result);
                    softAssertions.assertThat(result)
                            .matches(ans -> isValid(it, ans), "Rq: " + it);
                });

        softAssertions.assertAll();
    }

    @Test
    public void stressWithCheckWithoutWebTest() {
        SoftAssertions softAssertions = new SoftAssertions();

        IntStream.rangeClosed(1, 1000)
                .boxed()
                .map(it -> it % 2 == 0 ? MSK : SPB)
                .parallel()
                .forEach(it -> {
                    ApiAnswer result = doRequestWithoutWeb(it);
                    assertNotNull(result);
                    softAssertions.assertThat(result)
                            .matches(ans -> isValid(it, ans), "Rq: " + it);
                });

        softAssertions.assertAll();
    }

    @SneakyThrows
    ApiAnswer doRequest(String coord) {
        final ApiQuestion question = new ApiQuestion(new Person("Ярик", true, coord));
        final String answerStr = this.mockMvc.perform(post("/ask")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(question))
                )
                //.andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(answerStr, ApiAnswer.class);
    }

    @SneakyThrows
    ApiAnswer doRequestWithoutWeb(String coord) {
        final ApiQuestion question = new ApiQuestion(new Person("Ярик", true, coord));
        return controller.ask(question);
    }

    static boolean isValid(String coord, ApiAnswer answer) {
        return coord.contains("Petersburg") == answer.getResult().isUmbrella();
    }

    @Configuration
    public static class Config {
        @Bean
        public SimpleThreadScope simpleThreadScope() {
            return new SimpleThreadScope();
        }
        @Bean
        public static CustomScopeConfigurer scopeConfigurer(SimpleThreadScope threadScope) {
            CustomScopeConfigurer configurer = new CustomScopeConfigurer();
            configurer.addScope("thread", threadScope);
            return configurer;
        }
    }
}
