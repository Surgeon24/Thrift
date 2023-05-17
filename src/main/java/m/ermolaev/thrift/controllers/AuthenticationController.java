package m.ermolaev.thrift.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.servlet.http.HttpSession;
import m.ermolaev.thrift.domain.User;
import m.ermolaev.thrift.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class AuthenticationController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("users")
    @ResponseBody
    public List<User> getAll(){
        return userRepository.getAll();
    }


    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login/login");
        return modelAndView;
    }


    @PostMapping("/login")
    @ApiOperation(value = "Login to the app", notes = "notes...")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public ModelAndView login(@ApiParam(value = "This is a username", required = true)
                                  @RequestParam String username, @RequestParam String password, HttpSession session) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if(user != null){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("username", username);
            modelAndView.setViewName("redirect:/{username}/wallets");
            return modelAndView;
        } else {
            System.out.println("USER NOT FOUND!");
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("error", "Invalid username or password");
            modelAndView.setViewName("redirect:/login");
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
    public ModelAndView register(@RequestParam String username, @RequestParam String password, @RequestParam String confirm_password) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            userRepository.registerUser(username, password, confirm_password);
            modelAndView.addObject("success", "User registered successfully");
            modelAndView.addObject("username", username);
            modelAndView.setViewName("redirect:/{username}/wallets");
        } catch (Exception e) {
            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("redirect:/register");
        }
        return modelAndView;
    }
}
