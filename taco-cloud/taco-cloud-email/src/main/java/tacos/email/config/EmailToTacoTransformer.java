package tacos.email.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;
import tacos.dto.TacoDto;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@Component
public class EmailToTacoTransformer extends AbstractMailMessageTransformer<TacoDto> {

    private static final String SUBJECT_KEYWORDS = "TACO";

    @Override
    protected AbstractIntegrationMessageBuilder<TacoDto> doTransform(Message mailMessage) {
        TacoDto tacoDto = processPayload(mailMessage);

        return MessageBuilder.withPayload(tacoDto);
    }

    /**
     * 处理Email参数
     */
    private TacoDto processPayload(Message mailMessage) {
        try {
            String subject = mailMessage.getSubject();
            if (subject.toUpperCase().contains(SUBJECT_KEYWORDS)) {
                String email = ((InternetAddress) mailMessage.getFrom()[0]).getAddress();
                String content = mailMessage.getContent().toString();

                return parseEmailToTacoDto(email, content);
            }
        } catch (Exception e) {
            log.error("处理Email出错 {}", e.getMessage());
        }

        return new TacoDto();
    }

    /**
     * email example
     *
     * @param email   address
     * @param content FLTO, COTO, GRBF
     */
    private TacoDto parseEmailToTacoDto(String email, String content) {
        TacoDto tacoDto = new TacoDto();
        tacoDto.setName(email);
        tacoDto.setIngredients(new ArrayList<>());

        String[] lines = content.split("\\r?\\n");
        for (String line : lines) {
            if (line.trim().length() > 0) {
                String[] ingredientsId = line.split(",");

                tacoDto.getIngredients().addAll(Arrays.asList(ingredientsId));
            }
        }

        log.info("邮件Taco已生成: {}", JSON.toJSONString(tacoDto));

        return tacoDto;
    }
}
