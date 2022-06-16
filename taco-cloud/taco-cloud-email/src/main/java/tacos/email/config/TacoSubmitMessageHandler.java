package tacos.email.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tacos.dto.TacoDto;

@Slf4j
@Component
public class TacoSubmitMessageHandler implements GenericHandler<TacoDto> {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Object handle(TacoDto tacoDto, MessageHeaders headers) {
        if (tacoDto.getName() != null) {
            log.info("您有新的Taco预定: {}", JSON.toJSONString(tacoDto));
            restTemplate.postForObject("http://localhost:8080/design/design", tacoDto, Void.class);
        }

        return null;
    }
}
