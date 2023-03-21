package com.myboard.sbb.domain.question.controller;

import com.myboard.sbb.domain.question.entity.QuestionEntity;
import com.myboard.sbb.domain.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/list")
    public String showQuestions(Model model) {
        List<QuestionEntity> questions = questionService.getQList();
        model.addAttribute("questionList", questions);
        return "question/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") long id){
        QuestionEntity question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question/detail";
    }
}
