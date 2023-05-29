package com.example.demo.controller;

import com.example.demo.dto.AnswerVoteDTO;
import com.example.demo.entity.AnswerVote;
import com.example.demo.service.AnswerVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/answerVotes")
@CrossOrigin("http://localhost:4200")
public class AnswerVoteController {

    @Autowired
    AnswerVoteService answerVoteService;

    @GetMapping( "/getAll")
    @ResponseBody
    public List<AnswerVote> retrieveVotes() {
        return answerVoteService.retrieveVotes();
    }

    @GetMapping("/getById/{id}")
    @ResponseBody
    public AnswerVoteDTO retrieveById(@PathVariable Long id){
        return answerVoteService.retrieveVoteById(id);
    }

    @DeleteMapping("/deleteById/{id}")
    @ResponseBody
    public String deleteById(@PathVariable Long id){
        return answerVoteService.deleteById(id);
    }

    @PostMapping("/insertVote")
    @ResponseBody
    public AnswerVote insertVote(@RequestBody AnswerVote vote){
        return answerVoteService.saveVote2(vote);
    }

    @GetMapping("/getByUserAndAnswer/{userId}/{answerId}")
    @ResponseBody
    public AnswerVote getByUserAndAnswer(@PathVariable Long userId, @PathVariable Long answerId){
        return answerVoteService.getByUserAndAnswer(userId, answerId);
    }

    @PutMapping("/updateVote/{id}")
    @ResponseBody
    public ResponseEntity<AnswerVote> updateVote(@PathVariable Long id, @RequestBody AnswerVote answerVote){
        AnswerVote updatedVote = answerVoteService.updateVote(id,answerVote);
        if( answerVote!= null)
            return new ResponseEntity<>(updatedVote, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
