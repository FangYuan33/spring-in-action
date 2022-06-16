package tacos.email.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tacos.dto.TacoDto;

@Component
public class TacoSubmitMessageHandler implements GenericHandler<TacoDto> {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Object handle(TacoDto tacoDto, MessageHeaders headers) {
        restTemplate.postForObject("http://localhost:8080/design/design", tacoDto, Void.class);
        return null;
    }
}
