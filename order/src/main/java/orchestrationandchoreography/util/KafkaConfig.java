package orchestrationandchoreography.util;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;
    private final String orderTopic;
    private final String stockTopic;
    private final String paymentTopic;
    private final String notificationTopic;


    public KafkaConfig(KafkaProperties kafkaProperties,
                       @Value("${topic.name.order}") String orderTopic,
                       @Value("${topic.name.stock}") String stockTopic,
                       @Value("${topic.name.payment}") String paymentTopic,
                       @Value("${topic.name.notification}") String notificationTopic) {
        this.kafkaProperties = kafkaProperties;
        this.orderTopic = orderTopic;
        this.stockTopic = stockTopic;
        this.paymentTopic = paymentTopic;
        this.notificationTopic = notificationTopic;
    }


    @Bean
    public NewTopic createOrderTopic() {
        return TopicBuilder.name(orderTopic)
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic createStockTopic() {
        return TopicBuilder.name(stockTopic)
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic createPaymentTopic() {
        return TopicBuilder.name(paymentTopic)
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic createTestTopic() {
        return TopicBuilder.name(notificationTopic)
                .partitions(3)
                .compact()
                .build();
    }
}
