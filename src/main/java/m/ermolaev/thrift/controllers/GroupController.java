package m.ermolaev.thrift.controllers;

import m.ermolaev.thrift.domain.*;
import m.ermolaev.thrift.repositories.GroupRepository;
import m.ermolaev.thrift.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/{username}")
public class GroupController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;

    @GetMapping("/groups")
    public ModelAndView groupsPage(@PathVariable String username) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("groups/groups");
        modelAndView.addObject("username", username);
        List<Group> groups = userRepository.getAllGroups(username);
        modelAndView.addObject("groups", groups);
        List<Integer> members = new ArrayList<>();
        for (Group group : groups) {
            members.add(groupRepository.countMembers(group.getId()));
        }
        modelAndView.addObject("members", members);
        return modelAndView;
    }

    @GetMapping("/join_group")
    public ModelAndView joinGroupPage(@PathVariable String username){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("groups/join_group");
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    @PostMapping("/join_group")
    public ModelAndView createGroup(@PathVariable String username, @RequestParam String code){
        ModelAndView modelAndView = new ModelAndView();
        try {
            User user = userRepository.getUser(username);
            groupRepository.joinGroup(user.getId(), code);
            modelAndView.addObject("success", "Group joined successfully");
            modelAndView.setViewName("redirect:/{username}/groups");
        } catch (Exception e) {
            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("redirect:/{username}/join_group");
        }
        return modelAndView;
    }

    @GetMapping("/new_group")
    public ModelAndView createGroupPage(@PathVariable String username){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("groups/create_group");
        modelAndView.addObject("username", username);
        return modelAndView;
    }
    @PostMapping("/new_group")
    public ModelAndView createGroup(@PathVariable String username, @RequestParam String title, @RequestParam String description){
        ModelAndView modelAndView = new ModelAndView();
        try {
            User user = userRepository.getUser(username);
            groupRepository.addGroup(user.getId(), title, description);
            modelAndView.addObject("success", "Group added successfully");
            modelAndView.setViewName("redirect:/{username}/groups");
        } catch (Exception e) {
            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("redirect:/{username}/new_group");
        }
        return modelAndView;
    }


    @GetMapping("/group/{id_group}")
    public ModelAndView groupPage(@PathVariable String username, @PathVariable String id_group){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("groups/group_details");
        modelAndView.addObject("username", username);
        modelAndView.addObject("id_group", id_group);
        modelAndView.addObject("code", groupRepository.getCode(Integer.parseInt(id_group)));
        List<Group_expense> group_expenses = new ArrayList<>();
        group_expenses.addAll(groupRepository.getAllExpenses(Integer.parseInt(id_group)));
        modelAndView.addObject("group_expenses", group_expenses);
        modelAndView.addObject("group_title", groupRepository.getTitle(Integer.parseInt(id_group)));
        List<Integer> debts = new ArrayList<>();
        List<String> payers = new ArrayList<>();
        for (Group_expense ge : group_expenses) {
            debts.add(groupRepository.getDebt(ge.getId(), userRepository.getUserId(username)));
            payers.add(userRepository.getUserNickname(ge.getPayer_id()));
        }
        modelAndView.addObject("debts", debts);
        modelAndView.addObject("payers", payers);
        return modelAndView;
    }


    @GetMapping("/change_group/{id}")
    public ModelAndView changeGroupPage(@PathVariable String username, @PathVariable String id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView = groupPage(username, id);
        modelAndView.setViewName("groups/change_group");
        return modelAndView;
    }
}
