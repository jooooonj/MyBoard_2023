package com.myboard.sbb.domain.answer.controller;

import com.myboard.sbb.domain.answer.entity.AnswerEntity;
import com.myboard.sbb.domain.answer.form.AnswerForm;
import com.myboard.sbb.domain.answer.service.AnswerService;
import com.myboard.sbb.domain.question.entity.QuestionEntity;
import com.myboard.sbb.domain.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/create/{id}")
    public String createAnswer(@Valid AnswerForm answerForm, BindingResult bindingResult, @PathVariable("id") long id, Model model) {
        QuestionEntity question = questionService.getQuestion(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question/detail";
        }

        this.answerService.addAnswer(question, answerForm.getContent());
        return "redirect:/question/detail/"+ id;

    }
}
