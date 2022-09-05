package com.app.feedback.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
public class Form implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "form_id")
    private int id;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @PrePersist
    private void onCreate(){
        createdAt = new Date();
    }
   
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User createdBy;

    
    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
           name = "formQuestionMapping",
           joinColumns = @JoinColumn(name = "form_id", referencedColumnName = "form_id"),
           inverseJoinColumns = @JoinColumn(name = "question_id", referencedColumnName = "question_id")
            
    )
    private List<Question> questions = new ArrayList<>();
}
