package m.ermolaev.thrift.controllers;

import m.ermolaev.thrift.domain.User;
import m.ermolaev.thrift.domain.Wallet;
import m.ermolaev.thrift.domain.Wallet_expense;
import m.ermolaev.thrift.repositories.UserRepository;
import m.ermolaev.thrift.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/{username}")
public class WalletController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    WalletRepository walletRepository;

    @GetMapping("/wallets")
    public ModelAndView walletsPage(@PathVariable String username) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("wallets/wallets");
        modelAndView.addObject("username", username);
        System.out.println("debug in wallets\n");
        List<Wallet> wallets = userRepository.getAllWallets(username);
        modelAndView.addObject("wallets", wallets);
        List<List<Wallet_expense>> wallet_expenses = new ArrayList<>();
        List<Integer> spends = new ArrayList<>();
        List<Integer> limits = new ArrayList<>();
        for (Wallet wallet : wallets) {
            wallet_expenses.add(walletRepository.getAllExpenses(wallet.getId()));
            spends.add(walletRepository.getSpends(walletRepository.getAllExpenses(wallet.getId())));
            limits.add(walletRepository.getLimits(walletRepository.getAllExpenses(wallet.getId())));

        }
        modelAndView.addObject("wallet_expenses", wallet_expenses);
        modelAndView.addObject("spends", spends);
        modelAndView.addObject("limits", limits);
        return modelAndView;
    }

    @GetMapping("/new_wallet")
    public ModelAndView createWalletPage(@PathVariable String username){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("wallets/create_wallet");
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    @PostMapping("/new_wallet")
    public ModelAndView createWallet(@PathVariable String username, @RequestParam String title, @RequestParam String time_period){
        ModelAndView modelAndView = new ModelAndView();
        try {
            User user = userRepository.getUser(username);
            walletRepository.addWallet(user.getId(), title, time_period);
            modelAndView.addObject("success", "Investment added successfully");
            modelAndView.setViewName("redirect:/{username}/wallets");
        } catch (Exception e) {
            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("redirect:/{username}/new_wallet");
        }
        return modelAndView;
    }

    @GetMapping("/wallet/{id}")
    public ModelAndView walletPage(@PathVariable String username, @PathVariable String id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("wallets/wallet");
        modelAndView.addObject("username", username);
        modelAndView.addObject("id", Integer.parseInt(id));
        return modelAndView;
    }

    @GetMapping("/wallet/{id}/add_expense")
    public ModelAndView walletAddExpensePage(@PathVariable String username, @PathVariable String id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("wallets/add_expense");
        modelAndView.addObject("username", username);
        modelAndView.addObject("id", Integer.parseInt(id));
        return modelAndView;
    }

    @PostMapping("/wallet/{id}/add_expense")
    public ModelAndView walletAddExpense(@PathVariable String username, @PathVariable String id, @RequestParam String title, @RequestParam Integer limit){
        ModelAndView modelAndView = new ModelAndView();
        walletRepository.addExpense(Integer.parseInt(id), title, limit);
        modelAndView.setViewName("redirect:/{username}/wallets");
        return modelAndView;
    }

}
