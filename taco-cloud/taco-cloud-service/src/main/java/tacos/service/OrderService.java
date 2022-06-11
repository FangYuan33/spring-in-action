package tacos.service;

import tacos.domain.Order;
import tacos.dto.TacoOrderDto;

import java.util.List;

public interface OrderService {

    List<Order> listAll();

    void save(TacoOrderDto tacoOrderDto);

    void updateById(Long orderId, TacoOrderDto tacoOrderDto);
}
