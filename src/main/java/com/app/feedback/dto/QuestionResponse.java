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
public class QuestionResponse {
    private int question_id;
    private String QuestionText;
    private String answer;
    private String questionType;
    private List<String> options;
}
