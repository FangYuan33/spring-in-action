package tacos.domain;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TacoOrderTacos extends BaseEntity {

    private Long tacoOrder;

    private Long taco;

}
