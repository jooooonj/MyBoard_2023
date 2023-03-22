package com.myboard.sbb.domain.question.service;

import com.myboard.sbb.domain.question.entity.QuestionEntity;
import com.myboard.sbb.domain.question.repository.QuestionRepository;
import com.myboard.sbb.shared.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<QuestionEntity> getQuestions(int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<QuestionEntity> paging = questionRepository.findAll(pageable);
        return paging;
    }

    public QuestionEntity addQuestion(String subject, String content){
        QuestionEntity question = QuestionEntity
                .builder()
                .subject(subject)
                .content(content)
                .build();
        return questionRepository.save(question);
    }

}
