package tacos.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import tacos.domain.Order;
import tacos.repository.OrderRepository;

import javax.validation.Valid;

/**
 * 如果这个请求过来的时候，Session中没有携带order，那么会报错
 */
@Slf4j
@Controller
@SessionAttributes("order")
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    /**
     * @param order order对象可以从Session中获取到taco信息
     */
    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        log.info("Order Info: " + JSON.toJSONString(order));
        // 保存订单和对应连表信息
        orderRepository.save(order);

        // 保存完之后重置Session
        sessionStatus.setComplete();

        return "redirect:/design";
    }
}
