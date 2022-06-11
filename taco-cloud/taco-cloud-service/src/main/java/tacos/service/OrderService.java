package tacos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tacos.domain.Order;
import tacos.dto.TacoOrderDto;


public interface OrderService extends IService<Order> {

    void save(TacoOrderDto tacoOrderDto);

    void updateById(Long orderId, TacoOrderDto tacoOrderDto);
}
