package com.myboard.sbb.domain.question.service;

import com.myboard.sbb.domain.question.entity.QuestionEntity;
import com.myboard.sbb.domain.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<QuestionEntity> getQList(){
        return questionRepository.findAll();
    }
}
