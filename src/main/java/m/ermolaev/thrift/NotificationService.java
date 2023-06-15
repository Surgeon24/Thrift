package m.ermolaev.thrift;

import m.ermolaev.thrift.domain.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(NotificationService.class);
    public void sendNotification(Notification notification) {
        // Implementation of the logic that sends the notification to the appropriate recipient
        Integer recipient = notification.getRecipient();
        String message = notification.getMessage();

        storeNotification(notification);
        logger.info("Storing notification in database");

    }

    public void storeNotification(Notification notification) {
        Integer recipient = notification.getRecipient();
        String message = notification.getMessage();

        String sql = "INSERT INTO notifications (recipient, message) VALUES (?, ?)";
        jdbcTemplate.update(sql, recipient, message);
    }



}

