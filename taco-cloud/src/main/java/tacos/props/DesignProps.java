package tacos.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Data
@Component
@Profile("local")
@ConfigurationProperties("taco.design")
public class DesignProps {

    private Integer testProperties;
}
