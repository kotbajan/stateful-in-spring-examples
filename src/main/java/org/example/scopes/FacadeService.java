package org.example.scopes;

import lombok.extern.slf4j.Slf4j;
import org.example.scopes.model.ApiAnswer;
import org.example.scopes.model.ApiQuestion;
import org.example.scopes.model.ClothingSet;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class FacadeService {
    @Autowired BusinessLogic businessLogic;
    @Autowired ThreadScope threadScope;
    @Autowired
    @Qualifier("correlationId")
    ObjectProvider<String> correlationId;

    @Transactional
    public ApiAnswer calc(ApiQuestion rq) {
        try(var ignore = threadScope.init()) {
//            if ("Moscow".equals(rq.getPerson().getCoord())) {
//                correlationId.getObject("it's fine");
//                correlationId.getObject("it's no fine");
//            }
            final ClothingSet result = businessLogic.whatShouldIDo(rq.getPerson());
            final ApiAnswer rs = new ApiAnswer(result);
            log.debug("{}, In: {}, Out: {}, {}", correlationId.getObject(), rq, rs, businessLogic);
            return rs;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
