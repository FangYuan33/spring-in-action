package tacos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacos.domain.TacoOrderTacos;
import tacos.repository.TacoOrderTacosJPARepository;
import tacos.service.TacoOrderTacosService;

@Service
public class TacoOrderTacosServiceImpl implements TacoOrderTacosService {

    @Autowired
    private TacoOrderTacosJPARepository tacoOrderTacosJPARepository;

    @Override
    public void save(TacoOrderTacos tacoOrderTacos) {
        tacoOrderTacosJPARepository.save(tacoOrderTacos);
    }
}
