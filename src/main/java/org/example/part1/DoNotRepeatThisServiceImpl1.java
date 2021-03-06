package org.example.part1;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DoNotRepeatThisServiceImpl1 implements DoNotRepeatThisService {
    private final Repo repo;
    private final Transformer transformer;

    private String key;
    private Integer data;

    public Integer execute(String key) {
        this.key = key;

        load();
        transform();
        save();

        return this.data;
    }

    private void load() {
        data = repo.find(key);
    }

    private void transform() {
        data = transformer.transform(data);
    }

    private void save() {
        repo.save(key, data);
    }

    public String getKey() {
        return key;
    }
}
