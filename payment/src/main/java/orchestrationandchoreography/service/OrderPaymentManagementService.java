package orchestrationandchoreography.service;

import lombok.extern.slf4j.Slf4j;
import orchestrationandchoreography.entity.Order;
import orchestrationandchoreography.producer.KafkaProducer;
import orchestrationandchoreography.util.OrderStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderPaymentManagementService {

    private final PaymentService paymentService;
    private final KafkaProducer kafkaProducer;

    public OrderPaymentManagementService(PaymentService paymentService, KafkaProducer kafkaProducer) {
        this.paymentService = paymentService;
        this.kafkaProducer = kafkaProducer;
    }

    public Order processPayment(Order order){
        if (order.getPaymentStatus().equalsIgnoreCase(OrderStatus.NEW)) {
            return newOrder(order);
        } else if (order.getPaymentStatus().equalsIgnoreCase(OrderStatus.ROLLBACK)){
            paymentService.rollbackPayment(order);
        }
        return order;
    }

    private Order newOrder(Order order) {
        log.debug("new payment request {}", order.getOrderNumber());
        boolean result = paymentService.newPayment(order);
        if (result){
            log.debug("payment accepted {}", order.getOrderNumber());

            order.setPaymentStatus(OrderStatus.SUCCESS);
        } else {
            log.debug("payment denied {}", order.getOrderNumber());
            order.setPaymentStatus(OrderStatus.FAILED);
            order.setPaymentStatusReason("Payment denied");
        }
        kafkaProducer.send(order.getOrderNumber(), order);
        return order;
    }
}