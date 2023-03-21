package com.myboard.sbb.domain.question.repository;

import com.myboard.sbb.domain.question.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    QuestionEntity findBySubject(String subject);
    QuestionEntity findBySubjectAndContent (String subject, String content);

    List<QuestionEntity> findBySubjectLike(String subject);


    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE question_entity AUTO_INCREMENT=1;", nativeQuery = true)
    void clearAutoIncrement();
}
