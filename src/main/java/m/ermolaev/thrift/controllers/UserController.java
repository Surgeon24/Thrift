package m.ermolaev.thrift.controllers;

import m.ermolaev.thrift.domain.User;
import m.ermolaev.thrift.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController

public class UserController {
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


//    @GetMapping("/my-page")
//    public String myPage() {
//        Neo4jProperties.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//            // user is not authenticated, redirect to login page
//            return "redirect:/login";
//        } else {
//            // user is authenticated, display the page
//            // code to display the page
//        }
//    }

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
