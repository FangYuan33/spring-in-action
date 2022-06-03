package tacos.repository;

import tacos.domain.Order;

public interface OrderRepository {

    void save(Order order);
}
