package tacos.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tacos.domain.TacoIngredients;
import tacos.repository.mapper.TacoIngredientsMapper;
import tacos.service.TacoIngredientsService;

@Service
public class TacoIngredientsServiceImpl extends ServiceImpl<TacoIngredientsMapper, TacoIngredients>
        implements TacoIngredientsService {

}
