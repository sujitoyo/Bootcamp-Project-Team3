package com.app.feedback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.feedback.entity.Form;
import com.app.feedback.entity.User;


public interface FormRepository extends JpaRepository<Form, Integer>{
    
    public List<Form> getBycreatedBy(User any);
}
