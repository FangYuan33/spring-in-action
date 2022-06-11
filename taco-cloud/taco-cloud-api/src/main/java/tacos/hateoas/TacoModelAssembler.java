package tacos.hateoas;

import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import tacos.controller.DesignTacoController;
import tacos.domain.Taco;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class TacoModelAssembler extends RepresentationModelAssemblerSupport<Taco, TacoModel> {

    public TacoModelAssembler() {
        super(DesignTacoController.class, TacoModel.class);
    }

    @NotNull
    @Override
    public TacoModel toModel(@NotNull Taco taco) {
        // 初始化必要的参数
        TacoModel tacoModel = new TacoModel(taco);

        // 根据ID添加上每个taco的link
        Link link = linkTo(methodOn(DesignTacoController.class).tacoById(taco.getId())).withRel("self");
        tacoModel.add(link);

        return tacoModel;
    }
}
