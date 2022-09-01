package com.app.feedback.entity;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Response {
    
    @Id
    @GeneratedValue
    @Column(name = "response_id")
    private int id;
    
    
    private int form_id;

    
    private int user_id;

    @Column
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "QuestionsAndAnswers", joinColumns = @JoinColumn(name = "response_id"))
    private List<StoreResponse> responseBody = new ArrayList<>();

    @Temporal(TemporalType.DATE)
    private Date submittedOn;

    @PrePersist
    private void onCreate(){
        submittedOn = new Date();
    }
}
