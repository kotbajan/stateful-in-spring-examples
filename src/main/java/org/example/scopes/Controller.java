package org.example.scopes;

import org.example.scopes.model.ApiAnswer;
import org.example.scopes.model.ApiQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
public class Controller {
    @Autowired FacadeService facadeService;

    @PostMapping(path = "/ask")
    public ApiAnswer ask(@RequestBody ApiQuestion rq) {
        return facadeService.calc(rq);
    }
}
