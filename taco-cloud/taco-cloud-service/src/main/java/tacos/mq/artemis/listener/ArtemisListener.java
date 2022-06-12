package tacos.mq.artemis.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import tacos.domain.Order;

@Slf4j
@Component
@ConditionalOnProperty(value = "mq.enable", havingValue = "artemis")
public class ArtemisListener {

    /**
     * Artemis的监听器实现
     */
    @JmsListener(destination = "${artemis.orderQueue}")
    public void receiveOrder(Order order) {
        log.info("Artemis监听器收到订单消息: {}", JSON.toJSONString(order));
    }
}
