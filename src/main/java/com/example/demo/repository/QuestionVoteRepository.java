package com.example.demo.repository;

import com.example.demo.entity.QuestionVote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin("http://localhost:4200")
public interface QuestionVoteRepository extends CrudRepository<QuestionVote, Long> {
}
