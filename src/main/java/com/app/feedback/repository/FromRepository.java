package com.app.feedback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.feedback.entity.Form;

public interface FromRepository extends JpaRepository<Form, Integer>{
    
}
