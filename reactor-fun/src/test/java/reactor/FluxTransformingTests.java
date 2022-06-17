package reactor;

import lombok.Data;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class FluxTransformingTests {

    /**
     * 跳过前三个，只要后两个
     */
    @Test
    public void skipAFew() {
        Flux<String> countFlux = Flux.just("one", "two", "skip a few", "ninety nine", "one hundred").skip(3);

        countFlux.subscribe(System.out::println);
    }

    /**
     * 每秒发布一条，但是跳过了4秒，所以只剩下了一条
     */
    @Test
    public void skipFewSeconds() throws InterruptedException {
        Flux<String> skip = Flux.just("one", "two", "skip a few", "ninety nine", "one hundred")
                .delayElements(Duration.ofSeconds(1)).skip(Duration.ofSeconds(4));

        CountDownLatch countDownLatch = new CountDownLatch(1);

        skip.subscribe(x -> {
            countDownLatch.countDown();
            System.out.println(x);
        });

        countDownLatch.await();
    }

    /**
     * 只拿前三条
     */
    @Test
    public void take() {
        Flux<String> nationalParkFlux = Flux.just("Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Acadia").take(3);

        nationalParkFlux.subscribe(System.out::println);
    }

    /**
     * 只拿指定时间内的
     */
    @Test
    public void takeForAWhile() throws InterruptedException {
        Flux<String> nationalParkFlux = Flux.just("Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
                .delayElements(Duration.ofSeconds(1)).take(Duration.ofSeconds(5));

        CountDownLatch countDownLatch = new CountDownLatch(4);

        nationalParkFlux.subscribe(x -> {
            countDownLatch.countDown();
            System.out.println(x);
        });

        countDownLatch.await();
    }

    /**
     * 只要有空格的 过滤器这个很熟悉
     */
    @Test
    public void filter() {
        Flux<String> nationalParkFlux = Flux.just("Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
                .filter(x -> x.contains(" "));

        nationalParkFlux.subscribe(System.out::println);
    }

    /**
     * 去重
     */
    @Test
    public void distinct() {
        Flux<String> animalFlux = Flux.just("dog", "cat", "bird", "dog", "bird", "anteater").distinct();

        animalFlux.subscribe(System.out::println);
    }

    @Data
    private static class Player {
        private final String firstName;
        private final String lastName;
    }

    /**
     * 同步执行的映射
     */
    @Test
    public void map() {
        long start = System.currentTimeMillis();
        Flux<Player> playerFlux = Flux.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .map(item -> {
                    String[] names = item.split(" ");
                    return new Player(names[0], names[1]);
                });

        playerFlux.subscribe(System.out::println);

        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }

    /**
     * 异步执行的映射
     */
    @Test
    public void flatMap() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        Flux<Player> playerFlux = Flux.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .flatMap(item -> Flux.just(item)
                        .map(x -> {
                            countDownLatch.countDown();
                            String[] names = x.split(" ");
                            return new Player(names[0], names[1]);
                        }).log().subscribeOn(Schedulers.parallel())
                );


        playerFlux.subscribe();
        countDownLatch.await();
    }
}
