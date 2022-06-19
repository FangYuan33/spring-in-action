package tacos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import tacos.domain.Order;
import tacos.service.OrderService;


@Configuration
public class OrderFunctionConfig {

    @Autowired
    private OrderService orderService;

    @Bean
    public RouterFunction<ServerResponse> orderRouterFunction() {
        return RouterFunctions.route(RequestPredicates.GET("orders"),
                request -> ServerResponse.ok().body(Flux.fromIterable(orderService.list()), Order.class)
        );
    }
}
