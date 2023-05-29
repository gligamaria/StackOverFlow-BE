package com.example.demo;

import com.example.demo.entity.Question;
import com.example.demo.entity.User;
import com.example.demo.service.QuestionService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class DemoApplicationTests{

	@Autowired
	QuestionService questionService;

	@Autowired
	UserService userService;

	@Test
	public void getQuestion() {

		User user = userService.retrieveUserById(1L);
		LocalDateTime now = LocalDateTime.now();
		Question question = new Question(user,"titlu","text","picture.jpg",0, now);
//		questionService.saveQuestion(question);
		Question getQuestion = questionService.retrieveQuestionById(52L);

		Assertions.assertEquals(getQuestion.getTitle(),question.getTitle());
		Assertions.assertEquals(getQuestion.getText(),question.getText());
		Assertions.assertEquals(getQuestion.getScore(),question.getScore());
		Assertions.assertEquals(getQuestion.getPicture(),question.getPicture());

	}


}
