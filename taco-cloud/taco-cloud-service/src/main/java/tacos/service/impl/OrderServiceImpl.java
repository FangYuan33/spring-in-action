package tacos.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacos.domain.Order;
import tacos.domain.TacoOrderTacos;
import tacos.dto.TacoOrderDto;
import tacos.repository.mapper.OrderMapper;
import tacos.service.OrderService;
import tacos.service.TacoOrderTacosService;

import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private TacoOrderTacosService tacoOrderTacosService;

    @Override
    public void save(TacoOrderDto tacoOrderDto) {
        Order order = new Order().setOrderName(tacoOrderDto.getOrderName())
                .setAddress(tacoOrderDto.getAddress()).setPhone(tacoOrderDto.getPhone());
        baseMapper.insert(order);

        saveOrderDetails(tacoOrderDto.getTacoIds(), order.getId());
    }

    private void saveOrderDetails(List<Long> tacoIds, Long tacoOrderId) {
        for (Long tacoId : tacoIds) {
            TacoOrderTacos tacoOrderTacos = new TacoOrderTacos().setTaco(tacoId).setTacoOrder(tacoOrderId);

            tacoOrderTacosService.save(tacoOrderTacos);
        }
    }

    @Override
    public void updateById(Long orderId, TacoOrderDto tacoOrderDto) {
        Order order = (Order) new Order().setOrderName(tacoOrderDto.getOrderName())
                .setAddress(tacoOrderDto.getAddress()).setPhone(tacoOrderDto.getPhone()).setId(orderId);

        baseMapper.updateById(order);
    }
}
