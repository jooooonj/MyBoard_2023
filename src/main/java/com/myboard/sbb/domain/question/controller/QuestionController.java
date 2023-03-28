package com.myboard.sbb.domain.question.controller;

import com.myboard.sbb.domain.answer.form.AnswerForm;
import com.myboard.sbb.domain.question.entity.QuestionEntity;
import com.myboard.sbb.domain.question.form.QuestionForm;
import com.myboard.sbb.domain.question.service.QuestionService;
import com.myboard.sbb.domain.user.entity.SiteUser;
import com.myboard.sbb.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String showQuestions(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Page<QuestionEntity> paging = questionService.getQuestions(page);

        int blockPage = 5;
        int startPage = (page / blockPage) * blockPage + 1;
        int endPage = Math.min(startPage + blockPage - 1, paging.getTotalPages());

        model.addAttribute("questionList", paging);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "question/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(AnswerForm answerForm, Model model, @PathVariable("id") long id) {
        QuestionEntity question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String createForm(QuestionForm questionForm) {
        return "question/form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors())
            return "question/form";

        SiteUser user = userService.getUser(principal.getName());
        questionService.addQuestion(questionForm.getSubject(), questionForm.getContent(), user);
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable(value = "id") long id, Principal principal, QuestionForm questionForm) {
        QuestionEntity question = questionService.getQuestion(id);

        if (!question.getAuthor().getUserId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question/form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                         @PathVariable(value = "id") long id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question/form";
        }

        QuestionEntity question = questionService.getQuestion(id);

        if (!question.getAuthor().getUserId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/detail/"+ id;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") long id, Principal principal) {
        QuestionEntity question = questionService.getQuestion(id);

        if (!question.getAuthor().getUserId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        questionService.delete(question);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String vote(@PathVariable(value = "id") long id, Principal principal) {
        QuestionEntity question = questionService.getQuestion(id);
        SiteUser user = userService.getUser(principal.getName());
        questionService.vote(question, user);
        return "redirect:/question/detail/"+id;
    }
}
