package com.app.feedback.dto;

import java.util.List;

import com.app.feedback.entity.Question;
import com.app.feedback.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FormResponse {

    private int from_id;
    private String name;
    private User user;
    private List<Question> questions;
}
