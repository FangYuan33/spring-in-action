package tacos.config;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.domain.Ingredient;

import java.time.Duration;


@Slf4j
@Service
public class IngredientServiceClient {
    @Autowired
    private WebClient webClient;

    @HystrixCommand(fallbackMethod = "defaultList")
    public Flux<Ingredient> list() {
        // 需要处理cookie header等信息时使用exchange方法代替retrieve方法
        // 异常处理 超时处理
        return webClient.get().uri("/ingredient")
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.just(new RuntimeException("500的错误")))
                .bodyToFlux(Ingredient.class).timeout(Duration.ofSeconds(1)).log();
    }

    public Flux<Ingredient> defaultList() {
        log.error("测试断路器添加");

        return Flux.empty();
    }
}
