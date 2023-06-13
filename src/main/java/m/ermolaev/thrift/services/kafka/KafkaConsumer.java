package m.ermolaev.thrift.services.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "your_topic_name")
    public void consume(String message) {
        // Process the received message
        System.out.println("Received message: " + message);
    }
}
