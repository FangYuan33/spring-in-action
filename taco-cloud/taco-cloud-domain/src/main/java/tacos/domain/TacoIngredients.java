package tacos.domain;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TacoIngredients extends BaseEntity  {

    private Long taco;

    private String ingredient;

}
