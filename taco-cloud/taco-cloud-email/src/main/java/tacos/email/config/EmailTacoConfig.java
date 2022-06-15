package tacos.email.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.dsl.Mail;

@Configuration
public class EmailTacoConfig {

    @Bean
    public IntegrationFlow emailTacoFlow(EmailProperties emailProps,
                                         EmailToTacoTransformer emailToTacoTransformer,
                                         TacoSubmitMessageHandler tacoSubmitMessageHandler) {

        return IntegrationFlows
                .from(Mail.imapInboundAdapter(emailProps.getImapUrl()),
                        e -> e.poller(Pollers.fixedDelay(emailProps.getPollRate())))
                .transform(emailToTacoTransformer)
                .handle(tacoSubmitMessageHandler)
                .get();
    }
}
