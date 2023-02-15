package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminsController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AdminsController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("")
    public String createUserForm(ModelMap model, Principal principal) {
        model.addAttribute("admin", principal);
        model.addAttribute("users", userService.findAll());
        return "admin";
    }
    @GetMapping("/user-create")
    public ModelAndView createUserForm() {
        User user = new User();
        ModelAndView mav = new ModelAndView("user-create");
        mav.addObject("user", user);
        List<Role> roles = roleService.findAll();
        mav.addObject("roles", roles);
        return mav;
    }
    @PostMapping("/user-create")
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
    @GetMapping("/user-update/{id}")
    public ModelAndView updateUserForm(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        User userToUpdate = userService.findById(id);
        ModelAndView mav = new ModelAndView("user-update");
        mav.addObject("user", userToUpdate);
        List<Role> roles = roleService.findAll();
        mav.addObject("roles", roles);
        return mav;
    }

    @PutMapping("/user-update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }




}
