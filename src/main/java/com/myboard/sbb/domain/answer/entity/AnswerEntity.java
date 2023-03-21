package com.myboard.sbb.domain.answer.entity;

import com.myboard.sbb.domain.question.entity.QuestionEntity;
import com.myboard.sbb.shared.base.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AnswerEntity extends BaseEntity {
    @ManyToOne
    QuestionEntity question;
    @Column(columnDefinition = "TEXT")
    String content;

}
