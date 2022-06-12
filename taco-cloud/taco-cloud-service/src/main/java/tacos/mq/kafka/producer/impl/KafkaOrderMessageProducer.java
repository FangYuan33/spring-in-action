package tacos.mq.kafka.producer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import tacos.domain.Order;
import tacos.mq.OrderMessageProducer;
import tacos.mq.kafka.service.KafkaService;

@Service
@ConditionalOnProperty(value = "mq.enable", havingValue = "kafka")
public class KafkaOrderMessageProducer implements OrderMessageProducer {

    @Value("${kafka.orderTopic}")
    private String orderTopic;

    @Autowired
    private KafkaService kafkaService;

    @Override
    public boolean sendOrder(Order order) {
        return kafkaService.sendSync(orderTopic, order);
    }
}
