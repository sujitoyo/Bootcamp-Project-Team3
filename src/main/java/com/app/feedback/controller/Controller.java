package com.app.feedback.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.feedback.dto.FormRequest;
import com.app.feedback.dto.FormResponse;
import com.app.feedback.dto.QuestionRequest;
import com.app.feedback.dto.ResponseRequest;
import com.app.feedback.dto.UserRequest;
import com.app.feedback.dto.UserResponse;
import com.app.feedback.entity.Form;
import com.app.feedback.entity.Question;
import com.app.feedback.entity.QuetionType;
import com.app.feedback.entity.Response;
import com.app.feedback.entity.User;
import com.app.feedback.repository.FromRepository;
import com.app.feedback.repository.ResponseRepository;
import com.app.feedback.repository.UserRepository;

@RestController
public class Controller {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FromRepository fromRepository;

    @Autowired
    private ResponseRepository responseRepository;

    //Create Form API
    @PostMapping("/form")
    public FormResponse addform(@RequestBody FormRequest creatForm, @RequestParam("id") int user_id){
     
        User current_user = userRepository.getReferenceById(user_id);
        Form newform = new Form();
        newform.setName(creatForm.getName());
        List<Question> nQuestion = new ArrayList<>();
        List<QuestionRequest> questionRequests = creatForm.getQuestions();
        for(QuestionRequest qr : questionRequests){
            Question newQuestion = new Question();
            System.out.println(qr.getQuetionType());
            if(qr.getQuetionType() == "OBJECTIVE")
            newQuestion.setQuetionType(QuetionType.OBJECTIVE);
  
            else
            newQuestion.setQuetionType(QuetionType.SUBJECTIVE);

            newQuestion.setAnswers(qr.getAnswers());
            newQuestion.setText(qr.getText());
            nQuestion.add(newQuestion);

        }
        
        newform.setCreatedBy(current_user);
        newform.setQuestions(nQuestion);
        
        Form savedForm = fromRepository.save(newform);

        FormResponse sendForm = new FormResponse();
        sendForm.setFrom_id(savedForm.getId());
        sendForm.setName(savedForm.getName());
        sendForm.setQuestions(savedForm.getQuestions());
        sendForm.setUser(savedForm.getCreatedBy());

        return sendForm;
        

   }
   
   //submitFormAPI
   @PostMapping("/response")
   public String saveAllResponse(@RequestBody ResponseRequest responseRequest, @RequestParam("user_id") int user_id, @RequestParam("form_id") int form_id){
        
      Response newResponse = new Response();
      newResponse.setForm_id(form_id);
      newResponse.setUser_id(user_id);
      newResponse.setResponseBody(responseRequest.getQuestionAnswers());
      responseRepository.save(newResponse);
      return "added";
   }
  

   //to add user to database
   @PostMapping("/addUser")
    public UserResponse addUser(@RequestBody UserRequest first){
        User newUser = new User();
        newUser.setName(first.getName());
        newUser.setEmail(first.getEmail());

        User addedUser =userRepository.save(newUser);
        UserResponse sendUserResponse = new UserResponse();
        sendUserResponse.setId(addedUser.getId());
        sendUserResponse.setEmail(addedUser.getEmail());
        sendUserResponse.setName(addedUser.getName());
        
        return sendUserResponse;
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUser(){

        List<User> users = userRepository.findAll();
        List<UserResponse> allusers = new ArrayList<UserResponse>();

        for(User user: users){
           UserResponse a = new UserResponse();
           a.setId(user.getId());
           a.setEmail(user.getEmail());
           a.setName(user.getName());
           allusers.add(a);
        }

        return allusers;
    }

    @DeleteMapping("user/{id}")
    public void Deleteuser(@PathVariable int id){
        userRepository.deleteById(id);
    }

}
