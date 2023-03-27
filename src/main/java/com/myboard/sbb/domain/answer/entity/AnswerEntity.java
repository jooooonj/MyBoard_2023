package com.myboard.sbb.domain.answer.entity;

import com.myboard.sbb.domain.question.entity.QuestionEntity;
import com.myboard.sbb.domain.user.entity.SiteUser;
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
    private QuestionEntity question;
    @Column(columnDefinition = "TEXT")
    private String content;
    @ManyToOne
    private SiteUser author;

}
