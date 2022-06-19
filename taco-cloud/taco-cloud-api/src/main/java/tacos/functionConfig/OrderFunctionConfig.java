package tacos.functionConfig;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.domain.Order;
import tacos.dto.TacoOrderDto;
import tacos.mq.artemis.consumer.OrderMessageConsumer;
import tacos.service.OrderService;


@Configuration
public class OrderFunctionConfig {

    @Autowired
    private OrderService orderService;
    @Autowired(required = false)
    private OrderMessageConsumer orderMessageConsumer;

    private static final String PREFIX = "orders";

    @Bean
    public RouterFunction<ServerResponse> orderRouterFunction() {
        return RouterFunctions.route(RequestPredicates.GET(PREFIX), this::list)
                .andRoute(RequestPredicates.GET(PREFIX + "/cookNextOrder"), this::cookNextOrder)
                .andRoute(RequestPredicates.GET(PREFIX + "/{id}"), this::getOne)
                .andRoute(RequestPredicates.POST(PREFIX), this::createOrder)
                .andRoute(RequestPredicates.PUT(PREFIX + "/updateAll/{orderId}"), this::updateAllField)
                .andRoute(RequestPredicates.PATCH(PREFIX + "/updateAny/{orderId}"), this::updateAnyField)
                .andRoute(RequestPredicates.DELETE(PREFIX + "/{orderId}"), this::deleteOrder);
    }

    /**
     * 列表
     */
    @NotNull
    public Mono<ServerResponse> list(ServerRequest request) {
        return ServerResponse.ok().body(Flux.fromIterable(orderService.list()), Order.class);
    }


    @NotNull
    public Mono<ServerResponse> getOne(ServerRequest request) {
        String id = request.pathVariable("id");
        Order order = orderService.getById(id);

        if (order != null) {
            return ServerResponse.ok().body(Flux.just(order), Order.class);
        } else {
            return ServerResponse.ok().body(Flux.empty(), Void.TYPE);
        }
    }

    @NotNull
    public Mono<ServerResponse> createOrder(ServerRequest request) {
        Mono<TacoOrderDto> orderDtoMono = request.bodyToMono(TacoOrderDto.class);

        Mono<Mono<Object>> orderMono = orderDtoMono.map(item -> {
            orderService.save(item);
            return Mono.empty();
        });

        return ServerResponse.ok().body(orderMono, Void.TYPE);
    }

    /**
     * PutMapping 语义上是这对整体的更新（大规模）
     */
    @NotNull
    public Mono<ServerResponse> updateAllField(ServerRequest request) {
        return ServerResponse.ok().body(updateById(request), Void.TYPE);
    }

    /**
     * PatchMapping 语义上是对任一字段的更新
     */
    @NotNull
    public Mono<ServerResponse> updateAnyField(ServerRequest request) {
        return ServerResponse.ok().body(updateById(request), Void.TYPE);
    }

    private Mono<Mono<Object>> updateById(ServerRequest request) {
        Mono<TacoOrderDto> orderDtoMono = request.bodyToMono(TacoOrderDto.class);
        String orderId = request.pathVariable("orderId");

        return orderDtoMono.map(item -> {
            orderService.updateById(Long.parseLong(orderId), item);
            return Mono.empty();
        });
    }

    @NotNull
    public Mono<ServerResponse> deleteOrder(ServerRequest request) {
        String orderId = request.pathVariable("orderId");
        orderService.deleteOrder(Long.parseLong(orderId));

        return ServerResponse.ok().build();
    }

    @NotNull
    public Mono<ServerResponse> cookNextOrder(ServerRequest request) {
        Order order = orderMessageConsumer.receiveOrder();

        if (order != null) {
            return ServerResponse.ok().body(order, Order.class);
        } else {
            return ServerResponse.ok().build();
        }
    }
}
