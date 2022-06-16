package reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FluxCreationTests {

    @Test
    public void createAFluxJust() {
        Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Banana", "Strawberry");

        StepVerifier.create(fruitFlux)
                .expectNext("Apple").expectNext("Orange")
                .expectNext("Banana").expectNext("Strawberry").verifyComplete();
    }

    @Test
    public void createFluxArray() {
        String[] fruits = {"Apple", "Orange", "Banana", "Strawberry"};

        Flux<String> fruitFlux = Flux.fromArray(fruits);

        StepVerifier.create(fruitFlux)
                .expectNext("Apple").expectNext("Orange")
                .expectNext("Banana").expectNext("Strawberry").verifyComplete();
    }

    @Test
    public void createFluxIterable() {
        List<String> fruits = Arrays.asList("Apple", "Orange", "Banana", "Strawberry");

        Flux<String> fruitFlux = Flux.fromIterable(fruits);

        StepVerifier.create(fruitFlux)
                .expectNext("Apple").expectNext("Orange")
                .expectNext("Banana").expectNext("Strawberry").verifyComplete();
    }

    @Test
    public void createFluxStream() {
        Stream<String> fruits = Stream.of("Apple", "Orange", "Banana", "Strawberry");

        Flux<String> fruitFlux = Flux.fromStream(fruits);

        StepVerifier.create(fruitFlux)
                .expectNext("Apple").expectNext("Orange")
                .expectNext("Banana").expectNext("Strawberry").verifyComplete();
    }

    @Test
    public void createFluxInterval() {
        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1)).take(5);

        StepVerifier.create(intervalFlux)
                .expectNext(0L)
                .expectNext(1L)
                .expectNext(2L)
                .expectNext(3L)
                .expectNext(4L)
                .verifyComplete();
    }

    @Test
    public void createFluxRange() {
        Flux<Integer> rangeFlux = Flux.range(0, 5);

        StepVerifier.create(rangeFlux)
                .expectNext(0)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .verifyComplete();
    }
}
