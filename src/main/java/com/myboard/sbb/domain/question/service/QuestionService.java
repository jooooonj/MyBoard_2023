package com.myboard.sbb.domain.question.service;

import com.myboard.sbb.domain.answer.entity.AnswerEntity;
import com.myboard.sbb.domain.question.entity.QuestionEntity;
import com.myboard.sbb.domain.question.repository.QuestionRepository;
import com.myboard.sbb.domain.user.entity.SiteUser;
import com.myboard.sbb.domain.user.repository.UserRepository;
import com.myboard.sbb.shared.exception.DataNotFoundException;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
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

    public Page<QuestionEntity> getQuestions(int page, String kw) {
        int pageLimit = 10;
        Pageable pageable = PageRequest.of(page, pageLimit, Sort.Direction.DESC, "id");
        Specification<QuestionEntity> spec = search(kw);
        Page<QuestionEntity> paging = questionRepository.findAll(spec,pageable);
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

    private Specification<QuestionEntity> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<QuestionEntity> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<QuestionEntity, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<QuestionEntity, AnswerEntity> a = q.join("answerList", JoinType.LEFT);
                Join<AnswerEntity, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }

}
