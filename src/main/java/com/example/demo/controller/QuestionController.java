package com.example.demo.controller;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.entity.Answer;
import com.example.demo.entity.Question;
import com.example.demo.entity.Tag;
import com.example.demo.service.QuestionService;
import com.example.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping( "/questions")
@CrossOrigin("http://localhost:4200")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    TagService tagService;

    @GetMapping( value = "/getAll")
    @ResponseBody
    public List<Question> retrieveQuestions() {
        return questionService.retrieveQuestions();
    }

    @GetMapping("/getByIdDTO/{id}")
    @ResponseBody
    public QuestionDTO retrieveQuestionByIdDTO(@PathVariable Long id){
        return questionService.retrieveQuestionByIdDTO(id);
    }

    @GetMapping("/getById/{id}")
    @ResponseBody
    public Question retrieveQuestionById(@PathVariable Long id){
        return questionService.retrieveQuestionById(id);
    }

    @GetMapping("/getByTag/{tag}")
    @ResponseBody
    public List<Question> findByTag(@PathVariable String tag){
        return questionService.findByTag(tag);
    }

    @GetMapping("/getByAuthor/{name}")
    @ResponseBody
    public List<Question> retrieveQuestionsByAuthor(@PathVariable String name) {
        return questionService.retrieveQuestionByAuthor(name);}

    //ii DTO, change it daca ai nevoie altfel
    @GetMapping("/findByTitleContaining/{title}")
    @ResponseBody
    public List<Question> findByTitleContaining(@PathVariable String title){
        return questionService.findByTitleContaining(title);
    }

    @DeleteMapping("/deleteById/{id}")
    @ResponseBody
    public String deleteById(@PathVariable Long id){
        return questionService.deleteById(id);
    }

    @PostMapping("/insertQuestion")
    @ResponseBody
    public Question insertQuestion(@RequestBody Question question){
        return questionService.saveQuestion(question);
    }

    @PutMapping("/updateQuestion/{id}")
    @ResponseBody
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question){
        Question updatedQuestion = questionService.updateQuestion(id,question);
        if( updatedQuestion!= null)
            return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{questionId}/tags/{tagId}")
    public ResponseEntity<String> deleteTagFromQuestion(@PathVariable Long questionId, @PathVariable Long tagId) {
        Question question = questionService.retrieveQuestionById(questionId);

        Tag tag = tagService.retrieveTagById(tagId);

        if (question != null && tag != null) {
            boolean tagDeleted = question.deleteTag(tag.getTitle());
            if (tagDeleted) {
                questionService.saveQuestion(question);
                return ResponseEntity.ok("Tag deleted successfully");
            } else {
                return ResponseEntity.badRequest().body("Tag not found in question");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{questionId}/addTag")
    public ResponseEntity<String> addTagToQuestion(@PathVariable Long questionId, @RequestBody Tag tag) {
        Question question = questionService.retrieveQuestionById(questionId);
        if (question != null) {
            Tag myTag = tagService.getByTitle(tag.getTitle());
            question.addTag(myTag);
            questionService.saveQuestion(question);
            return ResponseEntity.ok("Tag added successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{questionId}/addAnswer")
    public ResponseEntity<String> addAnswerToQuestion(@PathVariable Long questionId, @RequestBody Answer answer) {
        Question question = questionService.retrieveQuestionById(questionId);
        if (question != null) {
            question.addAnswer(answer);
            questionService.saveQuestion(question);
            return ResponseEntity.ok("Answer added successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addTags")
    public List<Tag> addTags(@RequestBody List<String> tags) {

        List<Tag> questionTags = new ArrayList<>();
        for(String tagTitle:tags){
            Tag myTag = tagService.getByTitle(tagTitle);
            if(myTag == null){
                myTag = new Tag();
                myTag.setTitle(tagTitle);
                tagService.saveTag(myTag);
            }
            questionTags.add(myTag);
        }
        return questionTags;
    }
}
