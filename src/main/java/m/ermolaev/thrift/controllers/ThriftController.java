package m.ermolaev.thrift.controllers;

import m.ermolaev.thrift.domain.User;
import m.ermolaev.thrift.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController

public class ThriftController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("users")
    @ResponseBody
    public List<User> getAll(){
        return userRepository.getAll();
    }

    @GetMapping("test")
    @ResponseBody
    public String test(){
        return "test";
    }

    @GetMapping("/{nickname}/profile")
    public ModelAndView profilePage(@PathVariable String nickname) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        modelAndView.addObject("nickname", nickname);
        return modelAndView;
    }

    @GetMapping("/{nickname}/wallets")
    public ModelAndView walletsPage(@PathVariable String nickname) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("wallets");
        modelAndView.addObject("nickname", nickname);
        return modelAndView;
    }


    @GetMapping("/{nickname}/groups")
    public ModelAndView groupsPage(@PathVariable String nickname) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("groups");
        modelAndView.addObject("nickname", nickname);
        return modelAndView;
    }

    @GetMapping("/{nickname}/investments")
    public ModelAndView investmentsPage(@PathVariable String nickname) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("investments");
        modelAndView.addObject("nickname", nickname);
        return modelAndView;
    }
}
