package tacos.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import tacos.domain.Order;
import tacos.domain.Taco;
import tacos.domain.User;
import tacos.repository.OrderRepository;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final SimpleJdbcInsert orderInserter;

    private final SimpleJdbcInsert orderTacoInserter;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.orderInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("taco_order").usingGeneratedKeyColumns("id");
        this.orderTacoInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("taco_order_tacos");
    }

    @Override
    public void save(Order order) {
        long orderId = saveOrder(order);

        for (Taco taco : order.getTacos()) {
            saveTacoOrder(taco.getId(), orderId);
        }
    }

    private long saveOrder(Order order) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        order.setUserId(currentUser.getId());
        order.setPlacedAt(LocalDateTime.now());
        Map<String, Object> orderMap = objectToMap(order);

        return orderInserter.executeAndReturnKey(orderMap).longValue();
    }

    private Map<String, Object> objectToMap(Object object) {
        Map<String, Object> dataMap = new HashMap<>();
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                dataMap.put(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return dataMap;
    }

    private void saveTacoOrder(Long tacoId, long orderId) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("tacoOrder", orderId);
        map.put("taco", tacoId);
        orderTacoInserter.execute(map);
    }
}
