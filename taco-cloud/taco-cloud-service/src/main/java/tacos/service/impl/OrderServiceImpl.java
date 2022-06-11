package tacos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacos.domain.Order;
import tacos.domain.TacoOrderTacos;
import tacos.dto.TacoOrderDto;
import tacos.service.OrderService;
import tacos.repository.OrderJPARepository;
import tacos.service.TacoOrderTacosService;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderJPARepository orderJPARepository;
    @Autowired
    private TacoOrderTacosService tacoOrderTacosService;

    @Override
    public List<Order> listAll() {
        return (List<Order>) orderJPARepository.findAll();
    }

    @Override
    public void save(TacoOrderDto tacoOrderDto) {
        Order order = new Order().setOrderName(tacoOrderDto.getOrderName())
                .setAddress(tacoOrderDto.getAddress()).setPhone(tacoOrderDto.getPhone());
        Order id = orderJPARepository.save(order);

        saveOrderDetails(tacoOrderDto.getTacoIds(), id.getId());
    }

    private void saveOrderDetails(List<Long> tacoIds, Long tacoOrderId) {
        for (Long tacoId : tacoIds) {
            TacoOrderTacos tacoOrderTacos = new TacoOrderTacos().setTaco(tacoId).setTacoOrder(tacoOrderId);

            tacoOrderTacosService.save(tacoOrderTacos);
        }
    }

    @Override
    public void updateById(Long orderId, TacoOrderDto tacoOrderDto) {
        Order order = new Order().setId(orderId).setOrderName(tacoOrderDto.getOrderName())
                .setAddress(tacoOrderDto.getAddress()).setPhone(tacoOrderDto.getPhone());

        orderJPARepository.save(order);
    }
}
