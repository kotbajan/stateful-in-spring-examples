package org.example.scopes;

import lombok.extern.slf4j.Slf4j;
import org.example.scopes.model.ApiAnswer;
import org.example.scopes.model.ApiQuestion;
import org.example.scopes.model.ClothingSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class FacadeService {
    @Autowired BusinessLogic businessLogic;

    @Transactional
    public ApiAnswer calc(ApiQuestion rq) {
        final ClothingSet result = businessLogic.whatShouldIDo(rq.getPerson());
        final ApiAnswer rs = new ApiAnswer(result);
        log.debug("In: {}, Out: {}, {}", rq, rs, businessLogic);
        return rs;
    }
}
