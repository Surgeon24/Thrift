package m.ermolaev.thrift.controllers;

import m.ermolaev.thrift.domain.Notification;
import m.ermolaev.thrift.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/{username}")
public class ProfileController {

    @Autowired
    UserRepository userRepository;
    @GetMapping("/profile")
    public ModelAndView profilePage(@PathVariable String username) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        List<Notification> notifications = new ArrayList<>();
        notifications.addAll(userRepository.getNotifications(userRepository.getUserId(username)));
        modelAndView.addObject("username", username);
        modelAndView.addObject("notifications", notifications);
        return modelAndView;
    }
}
