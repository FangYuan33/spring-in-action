package tacos.mq;

import tacos.domain.Order;

public interface OrderMessageProducer {

    boolean sendOrder(Order order);
}
