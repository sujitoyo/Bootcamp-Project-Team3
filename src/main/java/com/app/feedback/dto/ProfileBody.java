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
public class ProfileBody {
    private List<FormBody> createdByUser;
    private List<FormBody> filledByUser;
    private List<FormBody> notFilledByUser;
}
