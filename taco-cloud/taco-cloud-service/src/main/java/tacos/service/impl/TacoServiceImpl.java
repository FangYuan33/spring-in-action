package tacos.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacos.dto.TacoDto;
import tacos.domain.Taco;
import tacos.domain.TacoIngredients;
import tacos.repository.mapper.TacoMapper;
import tacos.service.TacoIngredientsService;
import tacos.service.TacoService;

import java.util.List;

@Service
public class TacoServiceImpl extends ServiceImpl<TacoMapper, Taco> implements TacoService {

    @Autowired
    private TacoIngredientsService tacoIngredientsService;

    @Override
    public void saveTaco(TacoDto tacoDto) {
        Taco taco = new Taco().setName(tacoDto.getName());
        baseMapper.insert(taco);

        saveTacoDetails(tacoDto.getIngredients(), taco.getId());
    }

    private void saveTacoDetails(List<String> ingredients, Long id) {
        for (String ingredient : ingredients) {
            TacoIngredients tacoIngredients = new TacoIngredients().setTaco(id).setIngredient(ingredient);

            tacoIngredientsService.save(tacoIngredients);
        }
    }

}
