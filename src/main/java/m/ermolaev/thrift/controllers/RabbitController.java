package m.ermolaev.thrift.controllers;

import m.ermolaev.thrift.domain.Notification;
import m.ermolaev.thrift.repositories.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RabbitController {
    Logger logger = LoggerFactory.getLogger(RabbitController.class);

    private final RabbitTemplate rabbitTemplate;
    @Autowired
    public RabbitController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    @Autowired
    GroupRepository groupRepository;
    @PostMapping("/emit")
    public ResponseEntity<String> emit(@RequestBody Notification notification) {
        logger.info("Emit to my Queue");
        rabbitTemplate.convertAndSend("myQueue", notification);
        return ResponseEntity.ok("Success emit queue");
    }

    @GetMapping("/test")
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView();
        logger.info("test");
        List<Integer> recipients = new ArrayList<>();
        recipients.addAll(groupRepository.getAllParticipants(1));
        logger.info(recipients.toString());
        modelAndView.addObject("recipients", recipients);
        modelAndView.setViewName("groups/test");
        return modelAndView;
    }


}
