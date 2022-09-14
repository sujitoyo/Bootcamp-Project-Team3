package com.app.feedback;

import com.app.feedback.entity.Form;
import com.app.feedback.entity.Question;
import com.app.feedback.entity.Response;
import com.app.feedback.entity.User;
import com.app.feedback.repository.FormRepository;
import com.app.feedback.repository.QuestionRepository;
import com.app.feedback.repository.ResponseRepository;
import com.app.feedback.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedbackApplicationTests {
	@Autowired
	UserRepository userRepo;
	@Autowired
	FormRepository formRepo;
	@Autowired
	QuestionRepository quesRepo;
	@Autowired
	ResponseRepository resRepo;

	@Test
	public void User(){
		List<User> list=userRepo.findAll();
		assertNotNull(userRepo.findById(1).get());
		assertTrue(list.size()==15);
		assertFalse("Daya nandu"==list.get(10).getName());
		assertThat(list).size().isGreaterThan(14);
		User user=userRepo.findById(1).get();
		assertEquals("Motam Shiva Teja",user.getName());
	}

	@Test
	public void Form(){
		List<Form> form=formRepo.findAll();
		assertNotNull(form.get(1));
		assertTrue(form.size()==7);
		assertTrue(37==form.get(3).getId());
		assertFalse("Remote Life Survey"==form.get(5).getName());
		assertThat(form).size().isLessThan(8);
		assertEquals("Employee Complaint",form.get(5).getName());
	}

	@Test
	public void Question(){
		List<Question> ques=quesRepo.findAll();
		assertThat(ques.get(6).getText()).isEqualTo("What is your name?");
		assertFalse(40==ques.size());
		assertThat(ques).size().isLessThan(43);
		assertEquals(42,ques.size());

	}

	@Test
	public void Response(){
		List<Response> res=resRepo.getByformId(58);
		assertEquals(3,res.size());
		assertEquals(58,res.get(1).getFormId());
		assertEquals(68,res.get(3).getId());
	}


}
