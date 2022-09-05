package com.app.feedback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.feedback.dto.FormBody;
import com.app.feedback.dto.FormRequest;
import com.app.feedback.dto.FormResponse;
import com.app.feedback.dto.LoginRequest;
import com.app.feedback.dto.ProfileBody;
import com.app.feedback.dto.QuestionResponse;
import com.app.feedback.dto.ReportBody;
import com.app.feedback.dto.ResponseRequest;
import com.app.feedback.dto.UserRequest;
import com.app.feedback.dto.UserResponse;
import com.app.feedback.sevices.AllService;

@RestController
public class Controller {
    
    @Autowired
    private AllService allService;

   
    @GetMapping("/login")
    public ResponseEntity<UserResponse> getLogin(@RequestBody LoginRequest loginreq) {

        try {
            UserResponse sendResponse=allService.loginauth(loginreq);
            if (sendResponse.getId()!=0) {
                return ResponseEntity.ok(sendResponse);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/profilepage") 
    public ResponseEntity<ProfileBody> getAllProfileInfo(@RequestParam("user_id") int user_id){
        try {

            ProfileBody sendData = allService.getAllForms(user_id);    
            return ResponseEntity.ok(sendData);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //view Report API
    @GetMapping("/viewreport")
    public ResponseEntity<ReportBody> currentReport(@RequestParam("form_id") int form_id){
        try {
            ReportBody sBody = allService.viewReport(form_id);
            return ResponseEntity.ok(sBody);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Create Form API
    @PostMapping("/createform")
    public ResponseEntity<FormBody> createform(@RequestBody FormRequest creatForm, @RequestParam("id") int user_id){
     
        try {

            FormBody sendForm = allService.addform(creatForm, user_id);
            return ResponseEntity.ok(sendForm);

        } catch (Exception e) {
            
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        
   }


   //fill response Api
   @GetMapping("/fillresponse")
   public ResponseEntity<FormResponse> fillResponse(@RequestParam("form_id") int form_id){

      try {
       
            FormResponse sendForm = allService.formDetails(form_id);
            return ResponseEntity.ok(sendForm);

      } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
         
   }

   
   //submitFormAPI
   @PostMapping("/submitform")
   public ResponseEntity<FormBody> submitForm(@RequestBody ResponseRequest responseRequest, @RequestParam("user_id") int user_id, @RequestParam("form_id") int form_id){
       try {
       
            FormBody sendResBody = allService.saveAllResponse(responseRequest, user_id, form_id);
            return ResponseEntity.ok(sendResBody);

       } catch (Exception e) {
       
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       } 
      
   }
  
   //view form API
   @GetMapping("/viewresponse")
   public ResponseEntity<List<QuestionResponse>> getResponse(@RequestParam("response_id") int response_id){

    try {
        
            List<QuestionResponse> sendResponse = allService.viewResponse(response_id);
            return ResponseEntity.ok(sendResponse);

    } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
   }
    

   //to add user to database
   @PostMapping("/addUser")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest first){

       try {
        UserResponse sendUserResponse = allService.addUser(first); 
        return ResponseEntity.ok(sendUserResponse);

       } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsers(){

        try {
            List<UserResponse> allusers = allService.getAllUser();
            return ResponseEntity.ok(allusers);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
