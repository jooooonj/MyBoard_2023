package com.myboard.sbb.domain.question.entity;

import com.myboard.sbb.domain.answer.entity.AnswerEntity;
import com.myboard.sbb.domain.user.entity.SiteUser;
import com.myboard.sbb.shared.base.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuestionEntity extends BaseEntity {
    @Column(length = 200)
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Builder.Default
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<AnswerEntity> answerList = new ArrayList<>();

    @ManyToOne
    private SiteUser author;

    @ManyToMany
    Set<SiteUser> voter;

    public void modify(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }
    public void addAnswer(AnswerEntity answer) {
        answerList.add(answer);
    }
    
    
}
