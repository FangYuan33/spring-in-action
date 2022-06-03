package tacos.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.domain.Ingredient;
import tacos.domain.Order;
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

    // 被@ModelAttribute注释的方法会在此controller的每个方法执行前被执行 ，如果有返回值，则自动将该返回值加入到ModelMap中
    // 搭配了@SessionAttributes 这样这个空order对象就会放到Session中，这样之后请求processDesign的时候会将order传入
    // 这样向order中添加taco信息即可在orderController中拿到
    @ModelAttribute(name = "order")
    private Order order() {
        return new Order();
    }

    @GetMapping
    public String showDesignForm(Model model) {
        initialAndAddIngredientsToView(model);
        model.addAttribute("taco", new Taco());

        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco taco, Errors errors, Model model, @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            initialAndAddIngredientsToView(model);
            return "design";
        }
        log.info("Save Taco Info: " + JSON.toJSONString(taco));
        Taco savedTaco = tacoRepository.save(taco);
        order.addTaco(savedTaco);

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
