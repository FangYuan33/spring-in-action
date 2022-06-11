package tacos.repository;

import org.springframework.data.repository.CrudRepository;
import tacos.domain.TacoIngredients;

public interface TacoIngredientsJPARepository  extends CrudRepository<TacoIngredients, Long> {

}
