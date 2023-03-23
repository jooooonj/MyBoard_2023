package com.myboard.sbb.domain.question.controller;

import com.myboard.sbb.domain.answer.form.AnswerForm;
import com.myboard.sbb.domain.question.entity.QuestionEntity;
import com.myboard.sbb.domain.question.form.QuestionForm;
import com.myboard.sbb.domain.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/list")
    public String showQuestions(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Page<QuestionEntity> paging = questionService.getQuestions(page);

        int blockPage = 5;
        int startPage = (page / blockPage) * blockPage + 1;
        int endPage = Math.min(startPage + blockPage -1, paging.getTotalPages());

        model.addAttribute("questionList", paging);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "question/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(AnswerForm answerForm, Model model, @PathVariable("id") long id){
        QuestionEntity question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question/detail";
    }

    @GetMapping("/create")
    public String createForm(QuestionForm questionForm){
        return "question/form";
    }

    @PostMapping("/create")
    public String create(@Valid QuestionForm questionForm, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "question/form";

        questionService.addQuestion(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/list";
    }

}
