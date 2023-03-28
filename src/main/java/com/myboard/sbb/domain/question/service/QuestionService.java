package com.myboard.sbb.domain.question.service;

import com.myboard.sbb.domain.question.entity.QuestionEntity;
import com.myboard.sbb.domain.question.repository.QuestionRepository;
import com.myboard.sbb.domain.user.entity.SiteUser;
import com.myboard.sbb.domain.user.repository.UserRepository;
import com.myboard.sbb.shared.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public QuestionEntity getQuestion(long id) {
        Optional<QuestionEntity> findQuestion = questionRepository.findById(id);
        if (findQuestion.isPresent()) {
            return findQuestion.get();
        }
        throw new DataNotFoundException("this Question is not found");
    }

    public Page<QuestionEntity> getQuestions(int page) {
        int pageLimit = 10;
        Pageable pageable = PageRequest.of(page, pageLimit, Sort.Direction.DESC, "id");
        Page<QuestionEntity> paging = questionRepository.findAll(pageable);
        return paging;
    }

    public QuestionEntity addQuestion(String subject, String content, SiteUser user) {
        QuestionEntity question = QuestionEntity
                .builder()
                .subject(subject)
                .content(content)
                .author(user)
                .build();

        user.addQuestion(question);
        return questionRepository.save(question);
    }


    public void modify(QuestionEntity question, String subject, String content) {
        question.modify(subject, content);
        questionRepository.save(question);
    }

    public void delete(QuestionEntity questionEntity) {
        questionRepository.delete(questionEntity);

    }

    public void vote(QuestionEntity question, SiteUser user) {
        question.getVoter().add(user);
        questionRepository.save(question);
    }
}
