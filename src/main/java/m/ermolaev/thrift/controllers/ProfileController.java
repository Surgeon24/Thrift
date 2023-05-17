package m.ermolaev.thrift.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/{username}")
public class ProfileController {

    @GetMapping("/profile")
    public ModelAndView profilePage(@PathVariable String username) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        modelAndView.addObject("username", username);
        return modelAndView;
    }
}
