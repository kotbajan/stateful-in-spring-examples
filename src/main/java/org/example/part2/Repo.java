package org.example.part2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class Repo {
    public Integer find(String key) {
        log.debug("load {}", key);
        return key.hashCode();
    }

    public void save(String key, Integer value) {
        log.debug("save {}: {}", key, value);
    }
}
