package com.app.feedback.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Entity
public class Question {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "question_id")
    private int id;
    
    @Column(name = "question_text", length = 1000)
    private String text;

    @Enumerated(EnumType.ORDINAL)
    private QuestionType quetionType;
    
    @Column(length = 1000)
    @ElementCollection
    private List<String> answers = new ArrayList<String>();

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @PrePersist
    private void onCreate(){
        createdAt = new Date();
    }
    
    @JsonBackReference
    @ManyToMany(mappedBy = "questions" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Form> forms = new ArrayList<>();
}
