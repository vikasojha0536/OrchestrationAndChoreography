package orchestrationandchoreography.controller;

import lombok.extern.slf4j.Slf4j;
import orchestrationandchoreography.entity.Order;
import orchestrationandchoreography.entity.OrderDAO;
import orchestrationandchoreography.entity.OrderPojoWrapper;
import orchestrationandchoreography.producer.KafkaProducer;
import orchestrationandchoreography.service.OrderService;
import orchestrationandchoreography.util.OrderConverter;
import orchestrationandchoreography.util.OrderGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    private final OrderConverter orderConverter;

    private final OrderGenerator orderGenerator;

    private final KafkaProducer kafkaProducer;

    public OrderController(OrderService orderService,
                           OrderConverter orderConverter, OrderGenerator orderGenerator, KafkaProducer kafkaProducer) {
        this.orderService = orderService;
        this.orderConverter = orderConverter;
        this.orderGenerator = orderGenerator;
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/order")
    public ResponseEntity<?> sendOrders(@RequestParam(name = "o") Optional<Long> orders,
                                        @RequestParam(name = "i") Optional<Long> items) {
        List<OrderDAO> orderDAOList = orderGenerator.generateNewOrderDAOs(orders, items);
        orderService.saveAll(orderDAOList);
        log.debug("Orders list {}", orderDAOList);
        List<Order> orderList = orderConverter.listOrderDaoToKafka(orderDAOList);
        orderList.forEach(order ->
                                  kafkaProducer.sendOrder(order.getOrderNumber(), order));
        return ResponseEntity.ok(new OrderPojoWrapper(orderList));
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getOrders() {
        Iterable<OrderDAO> all = orderService.getAll();
        List<Order> orderList = orderConverter.listOrderDaoToKafka(all);
        return ResponseEntity.ok(new OrderPojoWrapper(orderList));
    }
}
