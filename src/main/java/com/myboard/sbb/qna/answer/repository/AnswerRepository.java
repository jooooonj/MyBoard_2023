package com.myboard.sbb.qna.answer.repository;

import com.myboard.sbb.qna.answer.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

}
