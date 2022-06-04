package tacos.repository;

import org.springframework.data.repository.CrudRepository;
import tacos.domain.Taco;

public interface TacoJPARepository extends CrudRepository<Taco, Long> {
}
