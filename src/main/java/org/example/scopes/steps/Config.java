package org.example.scopes.steps;

import org.example.scopes.model.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class Config {
    @Bean
    @Scope(scopeName = "thread")
    public Person person(Person p) {
        return p;
    }
}
