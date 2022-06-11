package tacos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacos.dto.TacoDto;
import tacos.domain.Taco;
import tacos.domain.TacoIngredients;
import tacos.service.TacoIngredientsService;
import tacos.service.TacoService;
import tacos.repository.TacoJPARepository;

import java.util.List;

@Service
public class TacoServiceImpl implements TacoService {

    @Autowired
    private TacoJPARepository tacoRepository;

    @Autowired
    private TacoIngredientsService tacoIngredientsService;

    @Override
    public void saveTaco(TacoDto tacoDto) {
        Taco taco = new Taco().setName(tacoDto.getName());
        Taco id = tacoRepository.save(taco);

        saveTacoDetails(tacoDto.getIngredients(), id.getId());
    }

    private void saveTacoDetails(List<String> ingredients, Long id) {
        for (String ingredient : ingredients) {
            TacoIngredients tacoIngredients = new TacoIngredients().setTaco(id).setIngredient(ingredient);

            tacoIngredientsService.save(tacoIngredients);
        }
    }

}
