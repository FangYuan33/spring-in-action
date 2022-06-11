package tacos.repository;

import org.springframework.data.repository.CrudRepository;
import tacos.domain.TacoOrderTacos;

public interface TacoOrderTacosJPARepository extends CrudRepository<TacoOrderTacos, Long> {
}
