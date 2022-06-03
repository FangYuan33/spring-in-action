package tacos.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import tacos.domain.Ingredient;
import tacos.domain.Taco;
import tacos.repository.IngredientRepository;
import tacos.repository.TacoRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@SessionAttributes("order")
@RequestMapping("/design")
public class DesignTacoController {

    @Autowired
    private TacoRepository tacoRepository;

    private final IngredientRepository ingredientRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public String showDesignForm(Model model) {
        initialAndAddIngredientsToView(model);
        model.addAttribute("taco", new Taco());

        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco taco, Errors errors, Model model) {
        if (errors.hasErrors()) {
            initialAndAddIngredientsToView(model);
            return "design";
        }
        log.info("Save Taco Info: " + JSON.toJSONString(taco));
        tacoRepository.save(taco);

        // 重定向，关于它的内容可看README
        return "redirect:/orders/current";
    }

    /**
     * 初始化食材并添加供前端页面展示
     */
    private void initialAndAddIngredientsToView(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);

        // 根据食材种类分类之后添加到model中 以分类在前台页面展示
        Ingredient.Type[] typeValues = Ingredient.Type.values();
        for (Ingredient.Type typeValue : typeValues) {
            model.addAttribute(typeValue.toString().toLowerCase(), filterByType(ingredients, typeValue));
        }
    }

    /**
     * 根据类型获取对应种类的食材
     */
    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
}
