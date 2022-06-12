package tacos.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import tacos.domain.Order;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rest")
public class RestTemplateController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/taco/{id}")
    @SuppressWarnings("unchecked")
    public void tacoById(@PathVariable Long id) {
        List<Order> response =
                restTemplate.getForObject("http://localhost:8080/orders", List.class, id);

        log.info(String.valueOf(JSON.toJSON(response)));
    }
}
