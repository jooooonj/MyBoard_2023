package com.myboard.sbb.domain.answer.controller;

import com.myboard.sbb.domain.answer.entity.AnswerEntity;
import com.myboard.sbb.domain.answer.service.AnswerService;
import com.myboard.sbb.domain.question.entity.QuestionEntity;
import com.myboard.sbb.domain.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/create/{id}")
    public String createAnswer(@PathVariable("id") long id, Model model, @RequestParam String content) {
        QuestionEntity question = questionService.getQuestion(id);

        return String.format("redirect:/question/detail/%s", id);

    }
}
