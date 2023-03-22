package com.myboard.sbb.domain.answer.service;

import com.myboard.sbb.domain.answer.entity.AnswerEntity;
import com.myboard.sbb.domain.answer.repository.AnswerRepository;
import com.myboard.sbb.domain.question.entity.QuestionEntity;
import com.myboard.sbb.shared.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerEntity getAnswer(long id){
        Optional<AnswerEntity> findAnswer = answerRepository.findById(id);
        if (findAnswer.isPresent()) {
            return findAnswer.get();
        }
        throw new DataNotFoundException("this Question is not found");
    }

    public void addAnswer(QuestionEntity question, String content) {
        AnswerEntity answer = AnswerEntity
                .builder()
                .content(content)
                .question(question)
                .build();
        question.addAnswer(answer);
        answerRepository.save(answer);
    }

}
