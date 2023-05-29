package com.example.demo.controller;

import com.example.demo.dto.QuestionVoteDTO;
import com.example.demo.entity.AnswerVote;
import com.example.demo.entity.QuestionVote;
import com.example.demo.service.QuestionVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/questionVotes")
@CrossOrigin("http://localhost:4200")
public class QuestionVoteController {

    @Autowired
    QuestionVoteService questionVoteService;

    @GetMapping( "/getAll")
    @ResponseBody
    public List<QuestionVote> retrieveVotes() {
        return questionVoteService.retrieveVotes();
    }

    @GetMapping("/getById/{id}")
    @ResponseBody
    public QuestionVoteDTO retrieveById(@PathVariable Long id){
        return questionVoteService.retrieveVoteById(id);
    }

    @DeleteMapping("/deleteById/{id}")
    @ResponseBody
    public String deleteById(@PathVariable Long id){
        return questionVoteService.deleteById(id);
    }

    @PostMapping("/insertVote")
    @ResponseBody
    public QuestionVote insertVote(@RequestBody QuestionVote vote){
        return questionVoteService.saveVote2(vote);
    }

    @PutMapping("/updateVote/{id}")
    @ResponseBody
    public ResponseEntity<QuestionVote> updateVote(@PathVariable Long id, @RequestBody QuestionVote questionVote){
        QuestionVote updatedQuestion = questionVoteService.updateVote(id,questionVote);
        if( questionVote!= null)
            return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getByUserAndQuestion/{userId}/{questionId}")
    @ResponseBody
    public QuestionVote getByUserAndQuestion(@PathVariable Long userId, @PathVariable Long questionId){
        return questionVoteService.getByUserAndQuestion(userId, questionId);
    }

//    @PutMapping("/updateVote/{id}")
//    @ResponseBody
//    public QuestionVote updateVote(@PathVariable Long id, @RequestBody QuestionVote questionVote){
//        return questionVoteService.updateVote(id, questionVote);
//    }
}
