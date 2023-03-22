package com.myboard.sbb;

import com.myboard.sbb.domain.answer.entity.AnswerEntity;
import com.myboard.sbb.domain.answer.repository.AnswerRepository;
import com.myboard.sbb.domain.question.entity.QuestionEntity;
import com.myboard.sbb.domain.question.repository.QuestionRepository;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class SbbApplicationTests {
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@BeforeEach
	void beforeEach(){
		answerRepository.deleteAll();
		answerRepository.clearAutoIncrement();

		questionRepository.deleteAll();
		questionRepository.clearAutoIncrement();

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
	@Test
	@DisplayName("모든 질문 찾기")
	void findAllToQuestions() {
		List<QuestionEntity> questions = questionRepository.findAll();
		assertThat(3).isEqualTo(questions.size());

		QuestionEntity question = questions.get(0);
		assertThat("test1").isEqualTo(question.getSubject());
	}

	@Test
	@DisplayName("Id로 질문찾기")
	void findByIdToQuestion() {
		QuestionEntity question = questionRepository.findById(Long.valueOf(1)).orElse(null);
		assertThat(question.getSubject()).isEqualTo("test1");
	}

	@Test
	@DisplayName("제목으로 질문찾기")
	void findBySubjectToQuestion() {
		QuestionEntity question = questionRepository.findBySubject("test1");
		assertThat(question.getContent()).isEqualTo("content1");
	}

	@Test
	@DisplayName("제목과 내용으로 질문찾기")
	void findBySubjectAndContentToQuestion() {
		QuestionEntity question = questionRepository.findBySubjectAndContent("test1", "content1");
		assertThat(question.getContent()).isEqualTo("content1");
		assertThat(question.getSubject()).isEqualTo("test1");
	}

	@Test
	@DisplayName("like문법으로 찾기")
	void findBySubjectLikeToQuestion() {
		List<QuestionEntity> questionList = questionRepository.findBySubjectLike("t%");
		assertThat(2).isEqualTo(questionList.size());
	}

	@Test
	@DisplayName("수정 테스트")
	void updateToQuestion() {
		QuestionEntity question = questionRepository.findById(Long.valueOf(1)).orElse(null);
		question.setSubject("changeTest1");
		questionRepository.save(question);

		QuestionEntity updatedQuestion = questionRepository.findById(Long.valueOf(1)).orElse(null);
		assertEquals(updatedQuestion.getSubject(), "changeTest1");
	}

	@Test
	@DisplayName("삭제 테스트")
	void deleteToQuestion(){
		QuestionEntity question = questionRepository.findById(Long.valueOf(1)).orElse(null);
		assertNotNull(question);
		List<QuestionEntity> list = questionRepository.findAll();

		assertEquals(list.size(), 3);

		questionRepository.delete(question);
		assertEquals(questionRepository.count(), 2);
	}

	@Test
	@DisplayName("답변 조회하기")
	void checkAnswer(){
		QuestionEntity question = questionRepository.findById(Long.valueOf(3)).orElse(null);
		assertNotNull(question);

		AnswerEntity answer = AnswerEntity
				.builder()
				.question(question)
				.content("홍길동입니다.")
				.build();
		AnswerEntity savedAnswerEntity = answerRepository.save(answer);
	}

	@Test
	@DisplayName("답변에 연결된 질문 찾기 vs 질문에 달린 답변 찾기")
	@Transactional //테스트 환경에서는 리포지토리를 이용한 통신만 가능하다.
	void t009(){
		QuestionEntity question = this.questionRepository.findById(Long.valueOf(3)).orElse(null);
		assertNotNull(question);

		List<AnswerEntity> answerList = question.getAnswerList();
		assertEquals(1, answerList.size());
	}

	@Test
	@DisplayName("질문 리스트 가져오기")
	void t010(){
		List<QuestionEntity> questions = questionRepository.findAll();
		assertNotNull(questions);

		for(int i=0; i<questions.size(); i++){
			System.out.println(questions.get(i).getSubject());
		}
	}

	@Test
	void testt(){
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
			QuestionEntity question = QuestionEntity
					.builder()
					.content(content)
					.subject(subject)
					.build();
			questionRepository.save(question);
		}
	}
}
