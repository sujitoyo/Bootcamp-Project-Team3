package com.app.feedback.sevices;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;


import com.app.feedback.dto.FormBody;
import com.app.feedback.dto.FormRequest;
import com.app.feedback.dto.FormResponse;
import com.app.feedback.dto.LoginRequest;
import com.app.feedback.dto.ProfileBody;
import com.app.feedback.dto.QuestionRequest;
import com.app.feedback.dto.QuestionResponse;
import com.app.feedback.dto.ReportBody;
import com.app.feedback.dto.ReportResponse;
import com.app.feedback.dto.ResponseBody;
import com.app.feedback.dto.ResponseRequest;
import com.app.feedback.dto.UserRequest;
import com.app.feedback.dto.UserResponse;
import com.app.feedback.entity.Form;
import com.app.feedback.entity.Question;
import com.app.feedback.entity.Response;
import com.app.feedback.entity.StoreResponse;
import com.app.feedback.entity.User;
import com.app.feedback.repository.FormRepository;
import com.app.feedback.repository.QuestionRepository;
import com.app.feedback.repository.ResponseRepository;
import com.app.feedback.repository.UserRepository;


@Service
public class AllService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private QuestionRepository questionRepository;


    public UserResponse loginauth(LoginRequest loginreq) {

        Optional<User> xyz = Optional.ofNullable(userRepository.findByEmail(loginreq.getEmail()));
        User abc=userRepository.findByEmail(loginreq.getEmail());
        if (xyz.isPresent()) {
            UserResponse currentresponse=new UserResponse();
            currentresponse.setId(abc.getId());
            currentresponse.setName(abc.getName());
            currentresponse.setEmail(abc.getEmail());
            return currentresponse;
        } else {
            UserResponse currentresponse=new UserResponse();
            currentresponse.setId(0);
            currentresponse.setName("");
            currentresponse.setEmail("");
            return currentresponse;
        }
        
    }

    public ProfileBody getAllForms(int user_id){
            
            User currentUser = userRepository.getReferenceById(user_id);

            List<Form> allForms = formRepository.findAll();
            List<Form> userForms = formRepository.getBycreatedBy(currentUser);
            List<Response> submittedForms = responseRepository.getByuserId(user_id);
            
            Set<Integer> checkForms = new HashSet<Integer>(); 
            List<FormBody> userCreated = new ArrayList<>();
            List<FormBody> userSubmitted = new ArrayList<>();
            List<FormBody> remainingForms = new ArrayList<>();

            for(Form uform : userForms){
                int form_id = uform.getId();
                checkForms.add(form_id);
                String name = uform.getName();
                FormBody fBody = new FormBody();
                fBody.setId(form_id);
                fBody.setName(name);
                 userCreated.add(fBody);
            }

            for(Response sform : submittedForms){
                int form_id = sform.getFormId();
                checkForms.add(form_id);
                Form currentForm = formRepository.getReferenceById(form_id);
                String name = currentForm.getName();
                FormBody fBody = new FormBody();
                fBody.setId(sform.getId());
                fBody.setName(name);
                 userSubmitted.add(fBody);
            }

            for(Form form : allForms){
              int form_id = form.getId();

              if(checkForms.contains(form_id))
              continue;
              
              else{
                Form currentForm = formRepository.getReferenceById(form_id);
                String name = currentForm.getName();
                FormBody fBody = new FormBody();
                fBody.setId(form_id);
                fBody.setName(name);
                 remainingForms.add(fBody);
              }

            }

            ProfileBody sendData = new ProfileBody();
            sendData.setCreatedByUser(userCreated);
            sendData.setFilledByUser(userSubmitted);
            sendData.setNotFilledByUser(remainingForms);
            
            
            return sendData;
        
    }

    
    public ReportBody viewReport(int form_id){
        

            List<Response> allResponse = responseRepository.getByformId(form_id);
            long totalUser = userRepository.count();
            long responseUser = (long) allResponse.size();
            List<ReportResponse> newReportResponses = new ArrayList<>();
            
            for(Response res: allResponse){
                User presentUser = userRepository.getReferenceById(res.getUserId());
                ReportResponse currentReportResponse = new ReportResponse();

                String userName = presentUser.getName();
                List<ResponseBody> newResponseBody = new ArrayList<>(); 
                List<StoreResponse> qa = res.getResponseBody();
                for(StoreResponse ca: qa){
                    Question question = questionRepository.getReferenceById(ca.getId());
                    String questionText = question.getText();
                    String answerText = ca.getResponseText();
                    ResponseBody currentRBody = new ResponseBody();
                    currentRBody.setAnswer(answerText);
                    currentRBody.setQuestion(questionText);
                    newResponseBody.add(currentRBody);

                }
                currentReportResponse.setName(userName);
                currentReportResponse.setQuestionAnswers(newResponseBody);
                newReportResponses.add(currentReportResponse);

            }
            
            ReportBody sendReportBody = new ReportBody();
            sendReportBody.setRespondedUser(responseUser);
            sendReportBody.setAllUser(totalUser);
            sendReportBody.setSendResponse(newReportResponses);

            return sendReportBody;
            
       
    }

    
    public FormBody addform(FormRequest creatForm,int user_id){
     
            User current_user = userRepository.getReferenceById(user_id);
        Form newform = new Form();
        newform.setName(creatForm.getName());

        List<Question> nQuestion = new ArrayList<>();
        List<QuestionRequest> questionRequests = creatForm.getQuestions();
        for(QuestionRequest qr : questionRequests){
            Question newQuestion = new Question();
            
            newQuestion.setQuetionType(qr.getQuestionType());
            newQuestion.setAnswers(qr.getAnswers());
            newQuestion.setText(qr.getText());
            nQuestion.add(newQuestion);

        }
        
        newform.setCreatedBy(current_user);
        newform.setQuestions(nQuestion);

        //fromRepository.save(newform);
        
        Form savedForm = formRepository.save(newform);

        FormBody sendForm = new FormBody();
        //sendForm.setFrom_id(savedForm.getId());
        sendForm.setName(savedForm.getName());
        sendForm.setId(savedForm.getId());
        //sendForm.setUser(savedForm.getCreatedBy());

        return sendForm;
        
        
   }


  
   
   public FormResponse formDetails(int form_id){

     
       Form savedForm = formRepository.getReferenceById(form_id);

        FormResponse sendForm = new FormResponse();
        sendForm.setName(savedForm.getName());
        sendForm.setQuestions(savedForm.getQuestions());
        //System.out.println(savedForm.getQuestions());
        return sendForm;
      
         
   }

  
   public FormBody saveAllResponse(ResponseRequest responseRequest, int user_id, int form_id){
      
        Response newResponse = new Response();
        Form currentForm = formRepository.getReferenceById(form_id);

      newResponse.setFormId(form_id);
      newResponse.setUserId(user_id);
      newResponse.setResponseBody(responseRequest.getQuestionAnswers());
      Response savedResponse = responseRepository.save(newResponse);
      FormBody sendResBody = new FormBody();
      sendResBody.setId(savedResponse.getId());
      sendResBody.setName(currentForm.getName());

      return sendResBody;
       
      
   }
  
   
   public List<QuestionResponse> viewResponse(int response_id){

        Response getresponse = responseRepository.getReferenceById(response_id);
        List<QuestionResponse> sendResponse = new ArrayList<>();
        List<StoreResponse> responseList = getresponse.getResponseBody();
        for(StoreResponse sr: responseList){
            int question_id = sr.getId();
            String ans = sr.getResponseText();
            Question getQuestion = questionRepository.getReferenceById(question_id);
            String questionText = getQuestion.getText();
            QuestionResponse newResponse = new QuestionResponse();
            newResponse.setAnswer(ans);
            newResponse.setOptions(getQuestion.getAnswers());
            newResponse.setQuestionType(getQuestion.getQuetionType().toString());
            newResponse.setQuestionText(questionText);
            newResponse.setQuestion_id(question_id);
            sendResponse.add(newResponse);

        }

        return sendResponse;
    
   }
    

   public UserResponse addUser(UserRequest first){

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

   

}

