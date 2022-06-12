package tacos.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tacos.domain.Order;
import tacos.domain.TacoOrderTacos;
import tacos.dto.TacoOrderDto;
import tacos.mq.OrderMessageProducer;
import tacos.repository.mapper.OrderMapper;
import tacos.service.OrderService;
import tacos.service.TacoOrderTacosService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private TacoOrderTacosService tacoOrderTacosService;
    @Resource(name = "kafkaOrderMessageProducer")
    private OrderMessageProducer kafkaOrderMessageProducer;

    @Override
    @Transactional
    public void save(TacoOrderDto tacoOrderDto) {
        Order order = new Order().setOrderName(tacoOrderDto.getOrderName())
                .setAddress(tacoOrderDto.getAddress()).setPhone(tacoOrderDto.getPhone());
        baseMapper.insert(order);

        saveOrderDetails(tacoOrderDto.getTacoIds(), order.getId());

        // 发送异步订单消息给后厨
        kafkaOrderMessageProducer.sendOrder(order);
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

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        baseMapper.deleteById(orderId);
        tacoOrderTacosService.deleteByOrderId(orderId);
    }
}
