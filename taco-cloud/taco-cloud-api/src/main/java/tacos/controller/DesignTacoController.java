package tacos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tacos.domain.Ingredient;
import tacos.domain.Taco;
import tacos.repository.TacoJPARepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/design")
@Api(tags = "Tacos设计控制器")
public class DesignTacoController {

    // jdbc
    @Autowired
    private TacoJPARepository tacoRepository;

    @GetMapping("/recent")
    @ApiOperation(value = "获取最近的Tacos")
    public Iterable<Taco> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("id").descending());

        return tacoRepository.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID获取Taco")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> taco = tacoRepository.findById(id);

        return taco.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    /**
     * 初始化食材并添加供前端页面展示
     */
    private void initialAndAddIngredientsToView(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
//        ingredientRepository.findAll().forEach(ingredients::add);

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
