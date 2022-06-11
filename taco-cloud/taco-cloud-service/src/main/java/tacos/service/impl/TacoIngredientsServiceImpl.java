package tacos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacos.domain.TacoIngredients;
import tacos.service.TacoIngredientsService;
import tacos.repository.TacoIngredientsJPARepository;

@Service
public class TacoIngredientsServiceImpl implements TacoIngredientsService {

    @Autowired
    private TacoIngredientsJPARepository tacoIngredientsJPARepository;

    @Override
    public void save(TacoIngredients tacoIngredients) {
        tacoIngredientsJPARepository.save(tacoIngredients);
    }
}
