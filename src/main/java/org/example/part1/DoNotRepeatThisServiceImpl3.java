package org.example.part1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoNotRepeatThisServiceImpl3 implements DoNotRepeatThisService {
    private final Repo repo;
    private final Transformer transformer;
    private final Map<String, Integer> tmpStorage = new ConcurrentHashMap<>();

    public Integer execute(String key) {
        Integer data = load(key);
        data = transform(data);
        save(key, data);

        return data;
    }

    private Integer load(String key) {
        final Integer loadedValue = repo.find(key);
        tmpStorage.put(key, loadedValue);
        return loadedValue;
    }

    private Integer transform(Integer data) {
        return transformer.transform(data);
    }

    private void save(String key, Integer data) {
        log.debug("old value is {}", tmpStorage.get(key));
        repo.save(key, data);
    }
}
