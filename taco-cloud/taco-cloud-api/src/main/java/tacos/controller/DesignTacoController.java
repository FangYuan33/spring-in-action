package tacos.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.dto.TacoDto;
import tacos.domain.Taco;
import tacos.hateoas.TacoModel;
import tacos.hateoas.TacoModelAssembler;
import tacos.service.TacoService;


import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Slf4j
@RestController
@RequestMapping("/design")
@Api(tags = "Tacos设计控制器")
public class DesignTacoController {

    @Autowired
    private TacoService tacoService;

    @GetMapping("/recent")
    @ApiOperation(value = "获取最近的Tacos")
    public CollectionModel<TacoModel> recentTacos() {
        QueryWrapper<Taco> queryWrapper = new QueryWrapper<Taco>().orderByDesc(true, "id");

        List<TacoModel> result = tacoService.list(queryWrapper).stream()
                .map(item -> new TacoModelAssembler().toModel(item)).collect(Collectors.toList());

        WebMvcLinkBuilder link = linkTo(methodOn(DesignTacoController.class).recentTacos());
        return CollectionModel.of(result, link.withRel("recent"));
    }

    @GetMapping("/taco/{id}")
    @ApiOperation(value = "根据ID获取Taco")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(tacoService.getById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "设计Taco")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/design")
    public void designTaco(@RequestBody TacoDto tacoDto) {
        log.info("设计新的Taco: {}", JSON.toJSONString(tacoDto));
        tacoService.saveTaco(tacoDto);
    }
}
