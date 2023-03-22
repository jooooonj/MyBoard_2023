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
    @ResponseBody
    public Page<QuestionEntity> showQuestions(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Page<QuestionEntity> paging = questionService.getQuestions(page);
        model.addAttribute("paging", paging);
        return paging;
    }

    @GetMapping("/detail/{id}")
    public String detail(AnswerForm answerForm, Model model, @PathVariable("id") long id){
        QuestionEntity question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question/detail";
    }

    @GetMapping("/create")
    public String create(QuestionForm questionForm){
        return "question/form";
    }

    @PostMapping("/create")
    public String create(@Valid QuestionForm questionForm, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "redirect:/question/create";

        questionService.addQuestion(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/list";
    }


}
