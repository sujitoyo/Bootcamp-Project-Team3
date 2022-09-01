package com.app.feedback.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuestionRequest {
    
    private String text;

   
    private String quetionType;
  
    private List<String> answers;

}
