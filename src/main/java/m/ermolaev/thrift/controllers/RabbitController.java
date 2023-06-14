package m.ermolaev.thrift.controllers;

import m.ermolaev.thrift.rabbitmq.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RabbitController {
    Logger logger = LoggerFactory.getLogger(RabbitController.class);

    private final RabbitTemplate rabbitTemplate;
    @Autowired
    public RabbitController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/emit")
    public ResponseEntity<String> emit(@RequestBody Notification notification) {
        logger.info("Emit to my Queue");
        rabbitTemplate.convertAndSend("myQueue", notification);
        return ResponseEntity.ok("Success emit queue");
    }

}
