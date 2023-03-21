package com.myboard.sbb.domain.question.repository;

import com.myboard.sbb.domain.question.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    QuestionEntity findBySubject(String subject);
    QuestionEntity findBySubjectAndContent (String subject, String content);

    List<QuestionEntity> findBySubjectLike(String subject);

//    @Modifying query 어노테이션에서 실행되는 것이 UPDATE, DELETE, INSERT 라면 붙여야한다.

    @Query(value = "ALTER TABLE question AUTO_INCREMENT = 1", nativeQuery = true)
    void clearAutoIncrement();
}
