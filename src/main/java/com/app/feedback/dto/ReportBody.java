package com.app.feedback.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReportBody {

    private long allUser;
    private long respondedUser;
    private List<ReportResponse> sendResponse; 
    

}
