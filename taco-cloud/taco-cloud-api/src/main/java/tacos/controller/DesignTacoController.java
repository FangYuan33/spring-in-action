package tacos.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.dto.TacoDto;
import tacos.domain.Taco;
import tacos.service.TacoService;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/design")
@Api(tags = "Tacos设计控制器")
public class DesignTacoController {

    @Autowired
    private TacoService tacoService;

    @GetMapping("/recent")
    @ApiOperation(value = "获取最近的Tacos")
    public Iterable<Taco> recentTacos() {
        QueryWrapper<Taco> queryWrapper = new QueryWrapper<Taco>().orderByDesc(true, "id");

        return tacoService.list(queryWrapper);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID获取Taco")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(tacoService.getById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "设计Taco")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/design")
    public void designTaco(@RequestBody TacoDto tacoDto) {
        tacoService.saveTaco(tacoDto);
    }
}
