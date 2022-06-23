package tacos.email.config;

import com.alibaba.fastjson.JSON;
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

    @Override
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
}
