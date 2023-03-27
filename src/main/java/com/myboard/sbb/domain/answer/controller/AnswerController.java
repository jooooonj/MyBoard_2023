package com.myboard.sbb.domain.answer.controller;

import com.myboard.sbb.domain.answer.entity.AnswerEntity;
import com.myboard.sbb.domain.answer.form.AnswerForm;
import com.myboard.sbb.domain.answer.service.AnswerService;
import com.myboard.sbb.domain.question.entity.QuestionEntity;
import com.myboard.sbb.domain.question.service.QuestionService;
import com.myboard.sbb.domain.user.entity.SiteUser;
import com.myboard.sbb.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(@Valid AnswerForm answerForm, BindingResult bindingResult, @PathVariable("id") long id, Model model, Principal principal) {
        QuestionEntity question = questionService.getQuestion(id);
        SiteUser user = userService.getUser(principal.getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question/detail";
        }

        answerService.addAnswer(question, answerForm.getContent(), user);
        return "redirect:/question/detail/"+ id;

    }
}
