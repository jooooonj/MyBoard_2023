package com.myboard.sbb.domain.question.entity;

import com.myboard.sbb.domain.answer.entity.AnswerEntity;
import com.myboard.sbb.shared.base.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionEntity extends BaseEntity {
    @Column(length = 200)
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String content;
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<AnswerEntity> answerList;
}
