package com.example.demo.controller;

import com.example.demo.dto.AnswerDTO;
import com.example.demo.entity.Answer;
import com.example.demo.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/answers")
@CrossOrigin("http://localhost:4200")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @GetMapping( "/getAll")
    @ResponseBody
    public List<Answer> retrieveAnswers() {
        return answerService.retrieveAnswer();
    }

    @GetMapping("/getById/{id}")
    @ResponseBody
    public Answer retrieveAnswerById(@PathVariable Long id){
        return answerService.retrieveAnswerById(id);
    }

    @GetMapping("/getByIdDTO/{id}")
    @ResponseBody
    public AnswerDTO retrieveAnswerByIdDTO(@PathVariable Long id){
        return answerService.retrieveAnswerByIdDTO(id);
    }

    @GetMapping("/getByQuestionId/{id}")
    @ResponseBody
    public List<Answer> retrieveAnswersByQuestionId(@PathVariable Long id){
        return answerService.retrieveByQuestionId(id);
    }

    @DeleteMapping("/deleteById/{id}")
    @ResponseBody
    public String deleteById(@PathVariable Long id){
        return answerService.deleteById(id);
    }

    @PostMapping("/insertAnswer/{questionId}")
    @ResponseBody
    public Answer insertAnswer(@RequestBody Answer answer, @PathVariable Long questionId){
        return answerService.saveAnswer(answer, questionId);
    }

    @PutMapping("/updateAnswer/{id}")
    @ResponseBody
    public Answer updateAnswer(@PathVariable Long id, @RequestBody Answer answer){
        return answerService.updateAnswer(id, answer);
    }
}
