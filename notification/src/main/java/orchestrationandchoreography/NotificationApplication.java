package orchestrationandchoreography;

import orchestrationandchoreography.entity.Order;
import orchestrationandchoreography.service.NotificationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@ComponentScan(basePackages = "orchestrationandchoreography")
public class NotificationApplication {

    private final NotificationService notificationService;

    public NotificationApplication(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

    @KafkaListener(topics = "${topic.name.notification}")
    private void listener(Order order) {
        notificationService.sendNotification(order);
    }
}
