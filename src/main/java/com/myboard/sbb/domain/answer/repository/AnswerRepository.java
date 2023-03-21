package com.myboard.sbb.domain.answer.repository;

import com.myboard.sbb.domain.answer.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

}
