package com.myboard.sbb.shared.base.basedata;

import com.myboard.sbb.domain.answer.entity.AnswerEntity;
import com.myboard.sbb.domain.answer.repository.AnswerRepository;
import com.myboard.sbb.domain.question.entity.QuestionEntity;
import com.myboard.sbb.domain.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BaseData implements CommandLineRunner {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Override
    public void run(String... args) throws Exception {
        QuestionEntity question1 = QuestionEntity
                .builder()
                .subject("test1")
                .content("content1")
                .build();
        questionRepository.save(question1);

        QuestionEntity question2 = QuestionEntity
                .builder()
                .subject("test2")
                .content("content2")
                .build();
        questionRepository.save(question2);
        QuestionEntity question3 = QuestionEntity
                .builder()
                .subject("이름")
                .content("이름이뭔가요?")
                .build();
        questionRepository.save(question3);

        AnswerEntity answer = AnswerEntity
                .builder()
                .question(question3)
                .content("홍길동입니다.")
                .build();
        question3.addAnswer(answer);
        AnswerEntity savedAnswerEntity = answerRepository.save(answer);
    }
}
