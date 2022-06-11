package tacos.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tacos.domain.TacoOrderTacos;
import tacos.repository.mapper.TacoOrderTacosMapper;
import tacos.service.TacoOrderTacosService;

@Service
public class TacoOrderTacosServiceImpl extends ServiceImpl<TacoOrderTacosMapper, TacoOrderTacos>
        implements TacoOrderTacosService {

}
