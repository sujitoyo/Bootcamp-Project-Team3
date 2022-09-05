package com.app.feedback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.feedback.entity.Response;

public interface ResponseRepository extends JpaRepository<Response, Integer>{
    public List<Response> getByuserId(int id);
    public List<Response> getByformId(int id); 
}
