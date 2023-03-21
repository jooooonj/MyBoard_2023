package com.myboard.sbb.domain.question.service;

import com.myboard.sbb.domain.question.entity.QuestionEntity;
import com.myboard.sbb.domain.question.repository.QuestionRepository;
import com.myboard.sbb.shared.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionEntity getQuestion(long id){
        Optional<QuestionEntity> findQuestion = questionRepository.findById(id);
        if (findQuestion.isPresent()) {
            return findQuestion.get();
        }
        throw new DataNotFoundException("this Question is not found");
    }

    public List<QuestionEntity> getQList(){
        return questionRepository.findAll();
    }
}
