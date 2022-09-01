package com.app.feedback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.feedback.entity.Response;

public interface ResponseRepository extends JpaRepository<Response, Integer>{
    
}
