package tacos.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * 我自己的健康状况指示器
 */
@Component
public class MyHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        if (hour > 12) {
            return Health.up().withDetail("Why", "Hello, I Love My Life!").build();
        }

        return Health.up().withDetail("Why", "I Love My Work!").build();
    }
}
