package org.sber.sberhomework20.controller;

import lombok.RequiredArgsConstructor;
import org.sber.sberhomework20.dto.UserCreateDto;
import org.sber.sberhomework20.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public String create(Model model) {
        model.addAttribute("user", new UserCreateDto());
        return "index";
    }

    @PostMapping
    public String create(UserCreateDto userCreateDto) {
        userService.createUser(userCreateDto);
        return "redirect:/users";
    }
}
