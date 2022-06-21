package tacos.functionConfig;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;
import tacos.domain.User;
import tacos.repository.mapper.UserRepository;

@Configuration
public class UserFunctionConfig {

    private static final String PREFIX = "/user";

    @Autowired
    private UserRepository userRepository;

    @Bean
    RouterFunction<ServerResponse> userRouterFunction() {
        return RouterFunctions.route(RequestPredicates.POST(PREFIX + "/save"), this::save)
                .andRoute(RequestPredicates.GET(PREFIX + "/list"), this::list);
    }

    @NotNull
    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);

        return ServerResponse.ok().body(userMono.map(item -> userRepository.insert(item)).log(), Mono.class);
    }

    @NotNull
    public Mono<ServerResponse> list(ServerRequest request) {
        return ServerResponse.ok().body(userRepository.findAll(), User.class);
    }
}
