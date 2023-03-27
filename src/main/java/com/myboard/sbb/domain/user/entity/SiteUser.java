package com.myboard.sbb.domain.user.entity;

import com.myboard.sbb.domain.answer.entity.AnswerEntity;
import com.myboard.sbb.domain.question.entity.QuestionEntity;
import com.myboard.sbb.shared.base.baseentity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SiteUser extends BaseEntity {

    @Column(unique = true)
    @NotNull
    private String userId;
    @NotNull
    private String password;
    @NotNull
    private String username;

    private String email;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<QuestionEntity> questionList = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<AnswerEntity> answerList = new ArrayList<>();

    public void addQuestion(QuestionEntity question){
        questionList.add(question);
    }
    public void addAnswer(AnswerEntity answer){
        answerList.add(answer);
    }
}
