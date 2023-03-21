package com.myboard.sbb;

import com.myboard.sbb.domain.answer.entity.AnswerEntity;
import com.myboard.sbb.domain.answer.repository.AnswerRepository;
import com.myboard.sbb.domain.question.entity.QuestionEntity;
import com.myboard.sbb.domain.question.repository.QuestionRepository;
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
class SbbApplicationTests {
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Test
	@DisplayName("모든 질문 찾기")
	@Transactional(readOnly = true)
	void findAllToQuestions() {
		List<QuestionEntity> questions = questionRepository.findAll();
		assertThat(3).isEqualTo(questions.size());

		QuestionEntity question = questions.get(0);
		assertThat("test1").isEqualTo(question.getSubject());
	}

	@Test
	@DisplayName("Id로 질문찾기")
	@Transactional(readOnly = true)
	void findByIdToQuestion() {
		QuestionEntity question = questionRepository.findById(Long.valueOf(1)).orElse(null);
		assertThat(question.getSubject()).isEqualTo("test1");
	}

	@Test
	@DisplayName("제목으로 질문찾기")
	@Transactional(readOnly = true)
	void findBySubjectToQuestion() {
		QuestionEntity question = questionRepository.findBySubject("test1");
		assertThat(question.getContent()).isEqualTo("content1");
	}

	@Test
	@DisplayName("제목과 내용으로 질문찾기")
	@Transactional(readOnly = true)
	void findBySubjectAndContentToQuestion() {
		QuestionEntity question = questionRepository.findBySubjectAndContent("test1", "content1");
		assertThat(question.getContent()).isEqualTo("content1");
		assertThat(question.getSubject()).isEqualTo("test1");
	}

	@Test
	@DisplayName("like문법으로 찾기")
	@Transactional(readOnly = true)
	void findBySubjectLikeToQuestion() {
		List<QuestionEntity> questionList = questionRepository.findBySubjectLike("t%");
		assertThat(2).isEqualTo(questionList.size());
	}

	@Test
	@DisplayName("수정 테스트")
	@Transactional
	void updateToQuestion() {
		QuestionEntity question = questionRepository.findById(Long.valueOf(1)).orElse(null);
		question.setSubject("changeTest1");
		questionRepository.save(question);

		QuestionEntity updatedQuestion = questionRepository.findById(Long.valueOf(1)).orElse(null);
		assertEquals(updatedQuestion.getSubject(), "changeTest1");
	}

	@Test
	@DisplayName("삭제 테스트")
	@Transactional
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
	@Transactional
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

	@Test
	@Transactional(readOnly = true)
	@DisplayName("질문 리스트 가져오기")
	void t010(){
		List<QuestionEntity> questions = questionRepository.findAll();
		assertNotNull(questions);

		for(int i=0; i<questions.size(); i++){
			System.out.println(questions.get(i).getSubject());
		}
	}
}
