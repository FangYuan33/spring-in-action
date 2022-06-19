package tacos.functionConfig;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.domain.Taco;
import tacos.dto.TacoDto;
import tacos.service.TacoService;

import java.util.List;


@Configuration
public class TacoFunctionConfig {

    private static final String PREFIX = "/taco";

    @Autowired
    private TacoService tacoService;

    @Bean
    public RouterFunction<ServerResponse> tacoFunction() {
        return RouterFunctions.route(RequestPredicates.GET(PREFIX + "/recent"), this::recent)
                .andRoute(RequestPredicates.GET(PREFIX + "/{id}"), this::tacoById)
                .andRoute(RequestPredicates.POST(PREFIX + "/design"), this::designTaco);
    }

    @NotNull
    private Mono<ServerResponse> recent(ServerRequest request) {
        return ServerResponse.ok().body(Flux.fromIterable(recentCollection()), Taco.class);
    }

    private List<Taco> recentCollection() {
        QueryWrapper<Taco> queryWrapper = new QueryWrapper<Taco>().orderByDesc(true, "id");
        return tacoService.list(queryWrapper);
    }

    @NotNull
    private Mono<ServerResponse> tacoById(ServerRequest request) {
        String id = request.pathVariable("id");
        Taco taco = tacoService.getById(id);

        if (taco != null) {
            return ServerResponse.ok().body(Mono.just(taco), Taco.class);
        } else {
            return ServerResponse.ok().build();
        }
    }

    @NotNull
    private Mono<ServerResponse> designTaco(ServerRequest request) {
        Mono<TacoDto> tacoDtoMono = request.bodyToMono(TacoDto.class);

        Mono<Mono<Object>> result = tacoDtoMono.map(item -> {
            tacoService.saveTaco(item);
            return Mono.empty();
        }).log();

        return ServerResponse.ok().body(result, Void.TYPE);
    }
}
