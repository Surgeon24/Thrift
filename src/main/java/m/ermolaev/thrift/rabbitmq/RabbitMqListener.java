package m.ermolaev.thrift.rabbitmq;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class RabbitMqListener {
    private Logger logger = LoggerFactory.getLogger(RabbitMqListener.class);

    @RabbitListener(queues = "myQueue")
    public void processNotification(Notification notification) {
        logger.info("Received notification: {}", notification);
        // Process the received notification
        // Implement the logic to send the notification to the appropriate recipient(s), e.g., via email, push notification, etc.
    }
}
