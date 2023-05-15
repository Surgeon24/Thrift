package m.ermolaev.thrift.controllers;

import m.ermolaev.thrift.domain.Group;
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
@RequestMapping("/{nickname}")
public class ManageController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    WalletRepository walletRepository;

//    @GetMapping("/groups")
//    public ModelAndView groupsPage(@PathVariable String nickname) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("groups");
//        modelAndView.addObject("nickname", nickname);
//        userRepository.getAllGroups(nickname);
//        modelAndView.addObject("groups", groups);
//        return modelAndView;
//    }

    @GetMapping("/groups")
    public ModelAndView groupsPage(@PathVariable String nickname) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("groups");
        modelAndView.addObject("nickname", nickname);
        return modelAndView;
    }

//    @GetMapping("/wallets")
//    public ModelAndView walletsPage(@PathVariable String nickname) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("wallets");
//        modelAndView.addObject("nickname", nickname);

//        List<Wallet> wallets = userRepository.getAllWallets(nickname);
//        modelAndView.addObject("wallets", wallets);
//        return modelAndView;
//    }

    @GetMapping("/wallets")
    public ModelAndView walletsPage(@PathVariable String nickname) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("wallets");
        modelAndView.addObject("nickname", nickname);

        List<Wallet> wallets = userRepository.getAllWallets(nickname);
        modelAndView.addObject("wallets", wallets);
        List<List<Wallet_expense>> wallet_expenses = new ArrayList<>();
        for (Wallet wallet : wallets) {
            wallet_expenses.add(walletRepository.getAllExpenses(wallet.getId()));
        }
        modelAndView.addObject("wallet_expenses", wallet_expenses);

        return modelAndView;
    }


}
