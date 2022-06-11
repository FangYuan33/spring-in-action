package tacos.domain;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Taco extends BaseEntity {

    private String name;
}
