package com.myboard.sbb;

import com.myboard.sbb.qna.answer.entity.AnswerEntity;
import com.myboard.sbb.qna.answer.repository.AnswerRepository;
import com.myboard.sbb.qna.question.entity.QuestionEntity;
import com.myboard.sbb.qna.question.repository.QuestionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Test
	@DisplayName("모든 질문 찾기")
	void findAllToQuestions() {
		List<QuestionEntity> questions = questionRepository.findAll();
		assertThat(2).isEqualTo(questions.size());

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

		assertEquals(list.size(), 2);

		questionRepository.delete(question);
		assertEquals(questionRepository.count(), 1);
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
	@Transactional
	@DisplayName("답변에 연결된 질문 찾기 vs 질문에 달린 답변 찾기")
	void t009(){
		QuestionEntity question = this.questionRepository.findById(Long.valueOf(3)).orElse(null);
		assertNotNull(question);

		List<AnswerEntity> answerList = question.getAnswerList();
		assertEquals(1, answerList.size());
	}
}
