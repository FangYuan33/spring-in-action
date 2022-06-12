package tacos.mq.artemis.producer.impl;

import com.alibaba.fastjson.JSON;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import tacos.domain.Order;
import tacos.mq.artemis.producer.OrderMessageProducer;

import javax.jms.JMSException;
import javax.jms.Message;

@Slf4j
@Service
public class OrderMessageProducerImpl implements OrderMessageProducer {

    @Setter
    @Value("${artemis.orderQueue}")
    private String orderQueue;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sendOrder(Order order) {
        log.info("发送给后厨的订单: {}", JSON.toJSONString(order));

        jmsTemplate.convertAndSend(orderQueue, order, this::addOrderSource);
    }

    /**
     * 消息的后置处理器
     */
    @NotNull
    private Message addOrderSource(Message message) throws JMSException {
        message.setStringProperty("ORDER_SOURCE", "WEB");

        return message;
    }
}