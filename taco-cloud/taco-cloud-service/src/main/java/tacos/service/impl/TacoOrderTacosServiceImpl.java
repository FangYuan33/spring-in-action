package tacos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tacos.domain.TacoOrderTacos;
import tacos.repository.mapper.TacoOrderTacosMapper;
import tacos.service.TacoOrderTacosService;

@Service
public class TacoOrderTacosServiceImpl extends ServiceImpl<TacoOrderTacosMapper, TacoOrderTacos>
        implements TacoOrderTacosService {

    @Override
    public void deleteByOrderId(Long orderId) {
        LambdaQueryWrapper<TacoOrderTacos> deleteWrapper =
                new QueryWrapper<TacoOrderTacos>().lambda().eq(TacoOrderTacos::getTacoOrder, orderId);

        baseMapper.delete(deleteWrapper);
    }
}
