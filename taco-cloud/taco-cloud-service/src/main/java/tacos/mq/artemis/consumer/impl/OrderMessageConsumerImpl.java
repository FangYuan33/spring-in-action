package tacos.mq.artemis.consumer.impl;

import com.alibaba.fastjson.JSON;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import tacos.domain.Order;
import tacos.mq.artemis.consumer.OrderMessageConsumer;

@Slf4j
@Service
@ConditionalOnProperty(value = "mq.enable", havingValue = "artemis")
public class OrderMessageConsumerImpl implements OrderMessageConsumer {

    @Setter
    @Value("${artemis.orderQueue}")
    private String orderQueue;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public Order receiveOrder() {
        Order order = (Order) jmsTemplate.receiveAndConvert(orderQueue);

        log.info("接受到的订单消息: {}", JSON.toJSONString(order));

        return order;
    }
}
