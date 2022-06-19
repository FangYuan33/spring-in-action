package tacos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import tacos.config.IngredientServiceClient;
import tacos.domain.Ingredient;
import tacos.service.IngredientService;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private IngredientServiceClient ingredientServiceClient;

    @GetMapping
    public List<Ingredient> list() {
        return ingredientService.list();
    }

    @GetMapping("/testWebClient")
    public Flux<Ingredient> testWebClient() {
        return ingredientServiceClient.list();
    }
}
