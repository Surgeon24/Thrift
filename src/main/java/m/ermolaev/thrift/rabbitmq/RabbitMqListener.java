package m.ermolaev.thrift.rabbitmq;


import m.ermolaev.thrift.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class RabbitMqListener {
    private Logger logger = LoggerFactory.getLogger(RabbitMqListener.class);

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = "myQueue")
    public void processNotification(Notification notification) {
        logger.info("Received notification: {}", notification);
        notificationService.sendNotification(notification);
    }
}
