package tacos.mq.kafka.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tacos.domain.Order;


@Slf4j
@Component
@ConditionalOnProperty(value = "mq.enable", havingValue = "kafka")
public class KafkaOrderMessageListener {

    @KafkaListener(topics = "${kafka.orderTopic}", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    private void receiveOrderMessage(Order order) {
        log.info("Kafka接收到订单消息: {}", JSON.toJSONString(order));
    }
}
