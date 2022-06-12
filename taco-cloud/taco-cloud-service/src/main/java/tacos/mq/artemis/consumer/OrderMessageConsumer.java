package tacos.mq.artemis.consumer;

import tacos.domain.Order;

public interface OrderMessageConsumer {

    Order receiveOrder();
}
