package tacos.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import tacos.domain.Taco;

public interface TacoJPARepository extends PagingAndSortingRepository<Taco, Long> {

}
