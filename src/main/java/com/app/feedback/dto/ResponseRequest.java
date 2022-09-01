package com.app.feedback.dto;

import java.util.List;


import com.app.feedback.entity.StoreResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseRequest {
    
    private List<StoreResponse> questionAnswers;
}
