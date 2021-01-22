package ru.gb.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.gb.exception.NotFoundException;
import ru.gb.persist.entity.User;
import ru.gb.persist.repo.RoleRepository;
import ru.gb.service.UserService;

import javax.validation.Valid;


@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;
    private RoleRepository roleRepository;

    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String indexUserPage(Model model){
        model.addAttribute("users",userService.findAll());
        return "user";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable(value = "id") Long id, Model model) {
        logger.info("Edit user with id {}", id);
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("user", userService.findById(id).orElseThrow(NotFoundException::new));
        return "user_form";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute(new User());
        return "user_form";
    }

    @PostMapping("/update")
    public String updateUser(@Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "user_form";
        }
        userService.save(user);
        return "redirect:/user";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id) {
        logger.info("Delete user with id {}", id);
        userService.deleteById(id);
        return "redirect:/user";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException e){
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
