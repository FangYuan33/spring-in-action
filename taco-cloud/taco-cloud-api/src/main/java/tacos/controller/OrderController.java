package tacos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tacos.domain.Order;
import tacos.dto.TacoOrderDto;
import tacos.service.OrderService;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "订单模块")
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @ApiOperation("获取所有订单")
    public List<Order> allOrders() {
        return orderService.listAll();
    }

    @PostMapping
    @ApiOperation("新增订单")
    public void createOrder(@RequestBody TacoOrderDto tacoOrderDto) {
        orderService.save(tacoOrderDto);
    }

    /**
     * PutMapping 语义上是这对整体的更新（大规模）
     */
    @PutMapping("/updateAll/{orderId}")
    public void updateAllField(@PathVariable Long orderId, @RequestBody TacoOrderDto tacoOrderDto) {
        orderService.updateById(orderId, tacoOrderDto);
    }

    /**
     * PatchMapping 语义上是对任一字段的更新
     */
    @PatchMapping("/updateAny/{orderId}")
    public void updateAnyField(@PathVariable Long orderId, @RequestBody TacoOrderDto tacoOrderDto) {
        orderService.updateById(orderId, tacoOrderDto);
    }
}
