package com.example.demo.repository;

import com.example.demo.entity.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin("http://localhost:4200")
public interface AnswerRepository extends CrudRepository<Answer, Long> {
}
