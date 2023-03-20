package com.myboard.sbb.qna.answer;

import com.myboard.sbb.qna.question.entity.QuestionEntity;
import com.myboard.sbb.shared.base.BaseEntity;
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
