package tacos.hateoas;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import tacos.domain.Taco;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
// relation 如果返回的是单个对象 json字段名是taco 多个时为tacos
@Relation(itemRelation = "taco", collectionRelation = "tacos")
public class TacoModel extends RepresentationModel<TacoModel> {

    private String name;

    private LocalDateTime createdAt;

    public TacoModel(Taco taco) {
        this.name = taco.getName();
        this.createdAt = taco.getCreatedAt();
    }
}
