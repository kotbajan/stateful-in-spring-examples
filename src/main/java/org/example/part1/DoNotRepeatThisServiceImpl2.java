package org.example.part1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoNotRepeatThisServiceImpl2 implements DoNotRepeatThisService {
    private final Repo repo;
    private final Transformer transformer;

    public Integer execute(String key) {
        Integer data = load(key);
        data = transform(data);
        save(key, data);

        return data;
    }

    private Integer load(String key) {
        return repo.find(key);
    }

    private Integer transform(Integer data) {
        return transformer.transform(data);
    }

    private void save(String key, Integer data) {
        repo.save(key, data);
    }
}
