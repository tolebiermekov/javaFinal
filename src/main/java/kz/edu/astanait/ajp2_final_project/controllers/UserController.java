package kz.edu.astanait.ajp2_final_project.controllers;

import kz.edu.astanait.ajp2_final_project.models.Role;
import kz.edu.astanait.ajp2_final_project.models.User;
import kz.edu.astanait.ajp2_final_project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String showIndex(){
        return "index";
    }

    @GetMapping("/registration")
    public ModelAndView showRegistrationPage() {
        ModelAndView mav = new ModelAndView("registration");
        List<String> groups = new LinkedList<>();
        for (int i = 1; i < 5; i++) {
            String digit = String.valueOf(i);
            groups.add("BDA-190" + digit);
        }
        mav.addObject("userForm", new User());
        mav.addObject("groupList", groups);
        return mav;
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm) {
        userService.register(userForm);
        return "login";
    }

    @GetMapping("/profile")
    public String viewProfile(HttpServletRequest httpServletRequest, Model model){
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        model.addAttribute("user",user);
        return "profile";
    }

    @GetMapping("/FormForUpdate/{id}")
    public String FormForUpdate(@PathVariable(value = "id") long id, Model model) {

        // get user from the service
        User user = userService.getUserById(id);

        // set user as a model attribute to pre-populate the form
        model.addAttribute("user", user);
        return "update_user1";
    }

    @PostMapping("/saveChange")
    public String saveUser(@ModelAttribute("user") User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User u = (User)session.getAttribute("user");
        Role role = u.getRole();
        user.setRole(role);

        userService.update(user);
        return "redirect:/index";
    }

}
