package com.myboard.sbb.qna.question.repository;

import com.myboard.sbb.qna.answer.entity.AnswerEntity;
import com.myboard.sbb.qna.question.entity.QuestionEntity;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    QuestionEntity findBySubject(String subject);
    QuestionEntity findBySubjectAndContent (String subject, String content);

    List<QuestionEntity> findBySubjectLike(String subject);
}
