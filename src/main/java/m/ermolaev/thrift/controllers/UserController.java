package m.ermolaev.thrift.controllers;

import jakarta.servlet.http.HttpSession;
import m.ermolaev.thrift.domain.User;
import m.ermolaev.thrift.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController

public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "users", produces = "application/json")
    @ResponseBody
    public List<User> getAll(){
        return userRepository.getAll();
    }

    @GetMapping(value = "login", produces = "application/json")
    public ModelAndView showLoginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login/login");
        return modelAndView;
    }


    @PostMapping(value = "login", produces = "application/json")
    public ModelAndView login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        System.out.println("method login POST is running...");
        User user = userRepository.findByUsernameAndPassword(username, password);
        if(user != null){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            SecurityContextHolder.getContext().setAuthentication(auth);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("username", username);
            modelAndView.setViewName("redirect:/{username}/wallets");
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("error", "Invalid username or password");
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }
    }

    @GetMapping(value = "register", produces = "application/json")
    public ModelAndView showRegisterPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login/register");
        return modelAndView;
    }

    @PostMapping(value = "register", produces = "application/json")
    public ModelAndView register(@RequestParam String username, @RequestParam String password) {
        System.out.println("method register POST is running...");
        ModelAndView modelAndView = new ModelAndView();
        try {
            userRepository.registerUser(username, password);
            modelAndView.addObject("success", "User registered successfully");
            modelAndView.addObject("username", username);
            modelAndView.setViewName("redirect:/{username}/wallets");
        } catch (Exception e) {
            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("redirect:/register");
        }
        return modelAndView;
    }


    @GetMapping(value = "{nickname}/profile", produces = "application/json")
    public ModelAndView profilePage(@PathVariable String nickname) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        modelAndView.addObject("nickname", nickname);
        return modelAndView;
    }

    @GetMapping(value = "/{nickname}/wallets", produces = "application/json")
    public ModelAndView walletsPage(@PathVariable String nickname) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("wallets");
        modelAndView.addObject("nickname", nickname);
        return modelAndView;
    }


    @GetMapping(value = "/{nickname}/groups", produces = "application/json")
    public ModelAndView groupsPage(@PathVariable String nickname) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("groups");
        modelAndView.addObject("nickname", nickname);
        return modelAndView;
    }

    @GetMapping(value = "/{nickname}/investments", produces = "application/json")
    public ModelAndView investmentsPage(@PathVariable String nickname) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("investments");
        modelAndView.addObject("nickname", nickname);
        return modelAndView;
    }
}
