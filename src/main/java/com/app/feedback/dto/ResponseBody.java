package com.app.feedback.dto;

import java.util.List;

import com.app.feedback.entity.QuestionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseBody {
    private String question;
    private QuestionType questionType;
    private List<String> options;
    private String answer;
}
