package com.myboard.sbb.domain.answer.repository;

import com.myboard.sbb.domain.answer.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE answer_entity AUTO_INCREMENT=1;", nativeQuery = true)
    void clearAutoIncrement();
}
