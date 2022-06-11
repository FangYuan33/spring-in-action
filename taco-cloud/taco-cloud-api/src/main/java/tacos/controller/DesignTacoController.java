package tacos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.dto.TacoDto;
import tacos.domain.Taco;
import tacos.service.TacoService;
import tacos.repository.TacoJPARepository;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/design")
@Api(tags = "Tacos设计控制器")
public class DesignTacoController {

    @Autowired
    private TacoJPARepository tacoRepository;

    @Autowired
    private TacoService tacoService;

    @GetMapping("/recent")
    @ApiOperation(value = "获取最近的Tacos")
    public Iterable<Taco> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("id").descending());

        return tacoRepository.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID获取Taco")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> taco = tacoRepository.findById(id);

        return taco.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "设计Taco")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/design")
    public void designTaco(@RequestBody TacoDto tacoDto) {
        tacoService.saveTaco(tacoDto);
    }
}
