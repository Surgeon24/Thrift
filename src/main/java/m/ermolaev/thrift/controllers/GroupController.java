package m.ermolaev.thrift.controllers;

import m.ermolaev.thrift.domain.Group;
import m.ermolaev.thrift.domain.User;
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
}
