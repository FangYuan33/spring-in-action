package reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class FluxMergingTests {

    @Test
    public void mergeFluxes() throws InterruptedException {
        // 每500毫秒发布一条
        Flux<String> names = Flux.just("FangYuan", "XiaoWang", "MengMeng").delayElements(Duration.ofMillis(500));

        // 订阅后250毫秒，每500秒发一条，这样就能保证先出人名再出水果了
        Flux<String> fruits = Flux.just("WaterMelon", "Apple", "Orange")
                .delaySubscription(Duration.ofMillis(250)).delayElements(Duration.ofMillis(500));

        Flux<String> mergeFlux = names.mergeWith(fruits);

        CountDownLatch countDownLatch = new CountDownLatch(6);

        mergeFlux.subscribe(x -> {
            countDownLatch.countDown();
            System.out.println(x);
        });

        countDownLatch.await();
    }

    /**
     * 合并成名字和水果一一对应的形式
     */
    @Test
    public void zipFluxes() {
        Flux<String> names = Flux.just("FangYuan", "XiaoWang", "MengMeng");
        Flux<String> fruits = Flux.just("WaterMelon", "Apple", "Orange");

        Flux<Tuple2<String, String>> zippedFlux = Flux.zip(names, fruits);

        zippedFlux.subscribe(x -> System.out.println(x.getT1() + ": " + x.getT2()));
    }

    /**
     * first来选出第一个源发布的值
     */
    @Test
    public void firstFlux() {
        Flux<String> names = Flux.just("FangYuan", "XiaoWang", "MengMeng").delaySubscription(Duration.ofMillis(100));
        Flux<String> fruits = Flux.just("WaterMelon", "Apple", "Orange");

        Flux<String> fruitFlux = Flux.firstWithSignal(names, fruits);

        fruitFlux.subscribe(System.out::println);
    }
}
