package com.myboard.sbb.domain.user.controller;

import com.myboard.sbb.domain.user.form.SignupForm;
import com.myboard.sbb.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/signup")
    public String signup(SignupForm signupForm){
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid SignupForm signupForm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors())
            return "user/signup";

        if (!signupForm.getPassword().equals(signupForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordIncorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "user/signup";
        }

        userService.register(signupForm);
        return "redirect:/";
    }

}
