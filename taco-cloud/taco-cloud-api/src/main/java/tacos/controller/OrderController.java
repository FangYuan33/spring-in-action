package tacos.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.domain.Order;
import tacos.dto.TacoOrderDto;
import tacos.mq.artemis.consumer.OrderMessageConsumer;
import tacos.service.OrderService;


@Slf4j
@RestController
@Api(tags = "订单模块")
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    // 未启用artemis时则不注入这个bean
    @Autowired(required = false)
    private OrderMessageConsumer orderMessageConsumer;

    @GetMapping
    @ApiOperation("获取所有订单")
    public Flux<Order> allOrders() {
        return Flux.fromIterable(orderService.list());
    }

    @GetMapping("/{id}")
    @ApiOperation("获取某一条订单")
    public Flux<Order> oneOrder(@PathVariable Long id) {
        Order order = orderService.getById(id);

        if (order != null) {
            return Flux.just(order);
        } else {
            return Flux.empty();
        }
    }

    @PostMapping
    @ApiOperation("新增订单")
    public Mono<Void> createOrder(@RequestBody TacoOrderDto tacoOrderDto) {
        orderService.save(tacoOrderDto);
        return Mono.empty();
    }

    /**
     * PutMapping 语义上是这对整体的更新（大规模）
     */
    @ApiOperation("全量更新订单")
    @PutMapping("/updateAll/{orderId}")
    public Mono<Void> updateAllField(@PathVariable Long orderId, @RequestBody TacoOrderDto tacoOrderDto) {
        orderService.updateById(orderId, tacoOrderDto);
        return Mono.empty();
    }

    /**
     * PatchMapping 语义上是对任一字段的更新
     */
    @ApiOperation("非全量更新订单")
    @PatchMapping("/updateAny/{orderId}")
    public Mono<Void> updateAnyField(@PathVariable Long orderId, @RequestBody TacoOrderDto tacoOrderDto) {
        orderService.updateById(orderId, tacoOrderDto);
        return Mono.empty();
    }

    @ApiOperation("删除订单")
    @DeleteMapping("/{orderId}")
    public Mono<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return Mono.empty();
    }

    @GetMapping("/cookNextOrder")
    @ApiOperation("厨师获取下一个需要做的订单")
    public Mono<Order> cookNextOrder() {
        Order order = orderMessageConsumer.receiveOrder();

        if (order != null) {
            return Mono.just(order);
        } else {
            return Mono.empty();
        }
    }
}
