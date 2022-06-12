package tacos.mq.artemis.producer;

import tacos.domain.Order;

public interface OrderMessageProducer {

    void sendOrder(Order order);
}
