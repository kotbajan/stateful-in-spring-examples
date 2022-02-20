package org.example.part2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class NotifyService {
    public void notify(String key, Integer value) {
        log.debug("notify {}: {}", key, value);
    }
}
