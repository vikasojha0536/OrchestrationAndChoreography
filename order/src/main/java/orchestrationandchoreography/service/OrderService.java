package orchestrationandchoreography.service;

import lombok.extern.slf4j.Slf4j;
import orchestrationandchoreography.entity.OrderDAO;
import orchestrationandchoreography.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDAO save(OrderDAO orderDAO){
        return orderRepository.save(orderDAO);
    }

    public Iterable<OrderDAO> getAll(){
        return orderRepository.findAll();
    }

    public <S extends OrderDAO> Iterable<S> saveAll(Iterable<S> entities) {
        return orderRepository.saveAll(entities);
    }

    public Optional<OrderDAO> findByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }
}
