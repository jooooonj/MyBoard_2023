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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        return "redirect:/question/detail/" + id;
    }

    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modifyForm(@PathVariable(value = "id") long id, Principal principal, AnswerForm answerForm) {
        AnswerEntity answer = answerService.getAnswer(id);

        if (!principal.getName().equals(answer.getAuthor().getUserId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        answerForm.setContent(answer.getContent());
        return "answer/form";
    }

    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modify(@Valid AnswerForm answerForm, BindingResult bindingResult,
                         @PathVariable(value = "id") long id, Principal principal) {
        if (bindingResult.hasErrors())
            return "answer/form";

        AnswerEntity answer = answerService.getAnswer(id);

        if (!principal.getName().equals(answer.getAuthor().getUserId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        answerService.modify(answer, answerForm.getContent());
        return "redirect:/question/detail/" + answer.getQuestion().getId();
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable(value = "id") long id, Principal principal){
        AnswerEntity answer = answerService.getAnswer(id);

        if (!principal.getName().equals(answer.getAuthor().getUserId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        answerService.delete(answer);

        return "redirect:/question/detail/" + answer.getQuestion().getId();
    }

    @GetMapping("/vote/{id}")
    @PreAuthorize("isAuthenticated()")
    public String vote(Principal principal, @PathVariable(value = "id") long id){
        SiteUser user = userService.getUser(principal.getName());
        AnswerEntity answer = answerService.getAnswer(id);

        answerService.vote(answer, user);

        return "redirect:/question/detail/" + answer.getQuestion().getId();
    }

}
