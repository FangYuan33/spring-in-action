package tacos.repository;

import org.springframework.data.repository.CrudRepository;
import tacos.domain.Order;

public interface OrderJPARepository extends CrudRepository<Order, Long> {


}
