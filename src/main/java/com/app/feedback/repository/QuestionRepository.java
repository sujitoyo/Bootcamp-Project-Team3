package com.app.feedback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.feedback.entity.Question;

public interface QuestionRepository extends JpaRepository<Question , Integer>{
    
}
