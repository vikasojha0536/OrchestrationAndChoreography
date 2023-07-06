package orchestrationandchoreography.util;

import orchestrationandchoreography.entity.Item;
import orchestrationandchoreography.entity.Order;
import orchestrationandchoreography.entity.OrderDAO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class OrderConverter {

     public Order orderDaoToKafka(OrderDAO orderDAO) {
             Order orderKafka = new Order(orderDAO.getOrderNumber(),
                     orderDAO.getItems().stream().map(itemDAO ->
                             new Item(itemDAO.getItemNumber(), itemDAO.getQuantity())).collect(Collectors.toList()),
                     orderDAO.getOrderPrice(), orderDAO.getStockStatus(), orderDAO.getStockStatusReason(), orderDAO.getPaymentStatus(), orderDAO.getPaymentStatusReason());
             return orderKafka;
    }

    public List<Order> listOrderDaoToKafka(List<OrderDAO> orderDAOList){
         return orderDAOList.stream().map(this::orderDaoToKafka).collect(Collectors.toList());

    }

    public List<Order> listOrderDaoToKafka(Iterable<OrderDAO> orderDAOList){
        List<OrderDAO> list = new ArrayList<>();
        for (OrderDAO orderDAO: orderDAOList) {
            list.add(orderDAO);
        }
        return listOrderDaoToKafka(list);
    }
}
