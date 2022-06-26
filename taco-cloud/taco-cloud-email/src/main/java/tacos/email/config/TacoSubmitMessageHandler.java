package tacos.email.config;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tacos.dto.TacoDto;
import tacos.email.feign.TacoDesignClient;

@Slf4j
@Component
public class TacoSubmitMessageHandler implements GenericHandler<TacoDto> {

    @Setter
    @Value("${feign.enable}")
    private boolean feignEnable;

    @Autowired
    private TacoDesignClient tacoDesignClient;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 指定断路器的方法为defaultMessageHandler，超时时间为 5000ms
     * execution.timeout.enabled 指定false时可以让这个调用无限时间等待
     *
     * 在30s内(metrics.rollingStats.timeInMilliseconds)调用次数超过2(circuitBreaker.requestVolumeThreshold)次，
     * 且失败率超过50%(circuitBreaker.errorThresholdPercentage)，那么断路器就会进入打开状态
     *
     * 断路器进入打开状态60s(circuitBreaker.sleepWindowInMilliseconds)后，则进入半打开状态，将会再次尝试调用原始的方法
     */
    @Override
    @HystrixCommand(fallbackMethod = "defaultMessageHandler",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "60000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "30000")
            })
    public Object handle(TacoDto tacoDto, MessageHeaders headers) {
        if (tacoDto.getName() != null) {
            log.info("您有新的Taco预定: {}", JSON.toJSONString(tacoDto));

            if (feignEnable) {
                // feign 调用
                tacoDesignClient.designTaco(tacoDto);
            } else {
                // 在这里直接指定上taco-cloud的serviceName代替了之前的 主机名 + 端口号的形式
                restTemplate.postForObject("http://taco-cloud/taco/design", tacoDto, Void.class);
            }
        }

        return null;
    }

    /**
     * 断路器的默认处理方法，需要有一样的方法签名，
     *
     * @param e 另外在参数上可以指定抛出的异常在断路器中进行处理
     */
    private Object defaultMessageHandler(TacoDto tacoDto, MessageHeaders headers, Throwable e) {
        log.error("预定发生的异常", e);
        log.error("{}预定失败", JSON.toJSONString(tacoDto));

        return null;
    }
}
