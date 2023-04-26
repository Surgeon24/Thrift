package m.ermolaev.thrift.controllers;

import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login/login");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        // Authenticate the user here
        User user = userRepository.findByUsernameAndPassword(username, password);
        if(user != null){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("username", username);
            modelAndView.setViewName("redirect:/{username}/wallets");
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("error", "Invalid username or password");
            modelAndView.setViewName("login");
            return modelAndView;
        }
    }

    @GetMapping("/register")
    public ModelAndView showRegisterPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login/register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@RequestParam String username, @RequestParam String password) {
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

//    @PostMapping("/login")
//    public ModelAndView login(@RequestParam String username, @RequestParam String password) {
//        // Authenticate the user here
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("username", username);
//        modelAndView.setViewName("redirect:/{username}/wallets");
//        return modelAndView;
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
