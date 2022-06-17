package reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

public class FluxBufferingTests {

    /**
     * 每3个被缓冲到一个集合中
     */
    @Test
    public void buffer() {
        Flux<String> fruitFlux = Flux.just("apple", "orange", "banana", "kiwi", "strawberry");

        Flux<List<String>> buffer = fruitFlux.buffer(3).log();

        buffer.subscribe();
    }

    @Test
    public void flatBuffer() {
        Flux<List<String>> fruitFlux = Flux.just("apple", "orange", "banana", "kiwi", "strawberry")
                .flatMap(item -> Flux.just(item).buffer(3).log().subscribeOn(Schedulers.parallel()));

        fruitFlux.subscribe();
    }

    @Test
    public void collectList() {
        Flux<String> fruitFlux = Flux.just("apple", "orange", "banana", "kiwi", "strawberry");

        fruitFlux.collectList().log().subscribe();
    }

    @Test
    public void collectMap() {
        Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");

        // key: x.charAt(0) value: x
        Mono<Map<Character, String>> mono = animalFlux.collectMap(x -> x.charAt(0));

        mono.subscribe(x -> System.out.println(x.get('a')));
    }

    /**
     * 且 都符合为true
     */
    @Test
    public void all() {
        Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");

        Mono<Boolean> hasA = animalFlux.all(item -> item.contains("a"));
        StepVerifier.create(hasA).expectNext(true).verifyComplete();

        Mono<Boolean> hasK = animalFlux.all(item -> item.contains("k"));
        StepVerifier.create(hasK).expectNext(false).verifyComplete();
    }

    @Test
    public void any() {
        Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");

        Mono<Boolean> hasA = animalFlux.any(item -> item.contains("a"));
        StepVerifier.create(hasA).expectNext(true).verifyComplete();

        Mono<Boolean> hasK = animalFlux.any(item -> item.contains("k"));
        StepVerifier.create(hasK).expectNext(true).verifyComplete();
    }
}
