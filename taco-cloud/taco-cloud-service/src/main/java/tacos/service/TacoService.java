package tacos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tacos.domain.Taco;
import tacos.dto.TacoDto;

public interface TacoService extends IService<Taco> {
    void saveTaco(TacoDto tacoDto);
}
