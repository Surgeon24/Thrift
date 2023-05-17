package m.ermolaev.thrift.controllers;

import m.ermolaev.thrift.domain.Savings;
import m.ermolaev.thrift.domain.User;
import m.ermolaev.thrift.repositories.SavingsRepository;
import m.ermolaev.thrift.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/{username}")
public class InvestmentController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    SavingsRepository savingsRepository;


    @GetMapping("/investments")
    public ModelAndView investmentsPage(@PathVariable String username) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("investments/investments");
        modelAndView.addObject("username", username);
        List<Savings> instruments = savingsRepository.getAllInstruments(username);
        modelAndView.addObject("instruments", instruments);

        return modelAndView;
    }

    @GetMapping("/new_investment")
    public ModelAndView createInvestmentPage(@PathVariable String username){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("investments/create_investment");
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    @PostMapping("/new_investment")
    public ModelAndView createInvestment(@PathVariable String username, @RequestParam String title, @RequestParam String description,
                                         @RequestParam Integer amount){
        ModelAndView modelAndView = new ModelAndView();
        try {
            User user = userRepository.getUser(username);
            savingsRepository.addInvestment(user.getId(), title, description, amount);
            modelAndView.addObject("success", "Investment added successfully");
            modelAndView.setViewName("redirect:/{username}/investments");
        } catch (Exception e) {
            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("redirect:/{username}/new_investment");
        }
        return modelAndView;
    }

    @PostMapping("/investment/{investment_id}/delete")
    public ModelAndView deleteInvestment(@PathVariable String username, @PathVariable String investment_id) {
        System.out.println("DeleteMapping of investment\n");
        ModelAndView modelAndView = new ModelAndView();
        try {
            savingsRepository.deleteInvestment(Integer.parseInt(investment_id));
            modelAndView.addObject("success", "Investment deleted successfully");
            modelAndView.setViewName("redirect:/{username}/investments");
        } catch (Exception e) {
            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("redirect:/{username}/investments");
        }
        return modelAndView;
    }

    @GetMapping("/investment/{investment_id}")
    public ModelAndView investmentPage(@PathVariable String username, @PathVariable String investment_id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("investments/investment");
        modelAndView.addObject("username", username);
        Savings investment = savingsRepository.getInvestment(Integer.parseInt(investment_id));
        modelAndView.addObject("investment", investment);
        return modelAndView;
    }

    @PostMapping("/investment/{investment_id}")
    public ModelAndView updateInvestment(@PathVariable String username, @PathVariable String investment_id) {
        System.out.println("DeleteMapping of investment\n");
        ModelAndView modelAndView = new ModelAndView();
        try {
//            savingsRepository.updateInvestment(Integer.parseInt(investment_id));
            modelAndView.addObject("success", "Investment deleted successfully");
            modelAndView.setViewName("redirect:/{username}/investments");
        } catch (Exception e) {
            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("redirect:/{username}/investments");
        }
        return modelAndView;
    }
}
