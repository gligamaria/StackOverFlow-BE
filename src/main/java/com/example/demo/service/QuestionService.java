package com.example.demo.service;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.entity.Answer;
import com.example.demo.entity.Question;
import com.example.demo.entity.Tag;
import com.example.demo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public List<Question> retrieveQuestions() {
        List<Question> questions = (List<Question>) questionRepository.findAll();
        questions.sort(Comparator.comparing(Question::getCreationTime));
        Collections.reverse(questions);
        return questions;
    }

    public QuestionDTO retrieveQuestionByIdDTO(Long id) {

        Optional<Question> question = questionRepository.findById(id);
        if(question.isPresent()) {
            for(Answer answer:question.get().filterAnswers()){
                System.out.println(answer.getAnswerId() + " " + answer.getScore());
            }
            return new QuestionDTO(question.get().getTitle(), question.get().getText(), question.get().getUser().getName());
        } else {
            return null;
        }
    }

    public Question retrieveQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Question does not exist")
        );
    }

    public String deleteById(Long id){
        try{
            questionRepository.deleteById(id);
            return "Success";
        }
        catch (Exception e){
            e.printStackTrace();
            return "Failed";
        }
    }

    public Question saveQuestion(Question question){

        return questionRepository.save(question);
    }

    public List<Question> retrieveQuestionByAuthor(String name) {

        List<Question> allQuestions = this.retrieveQuestions();
        List<Question> filteredQuestions = new ArrayList<>();

        for(Question question: allQuestions){
            if(question.getUser().getName().equals(name)){
                filteredQuestions.add(question);
            }
        }

        return filteredQuestions;

    }

    public List<Question> findByTitleContaining(String title) {

        List<Question> allQuestions = this.retrieveQuestions();
        List<Question> filteredQuestions = new ArrayList<>();

        for(Question question: allQuestions){
            if(question.getTitle().contains(title)){
                filteredQuestions.add(question);
            }
        }

        return filteredQuestions;
    }

    public Question updateQuestion(Long id, Question newQuestion) {

        Optional<Question> question = questionRepository.findById(id);
        if(question.isPresent()) {

            Question oldQuestion = question.get();
            // the user and the creation date&time attributes
            // should not change
            if(newQuestion.getTitle()!=null) {
                oldQuestion.setTitle(newQuestion.getTitle());
            }
            if(newQuestion.getText()!=null) {
                oldQuestion.setText(newQuestion.getText());
            }
            if(newQuestion.getPicture()!=null) {
                oldQuestion.setPicture(newQuestion.getPicture());
            }
            if(newQuestion.getQuestionTags()!=null){
                oldQuestion.setQuestionTags(newQuestion.getQuestionTags());
            }
            return questionRepository.save(oldQuestion);
        }
        return null;

    }

    public List<Question> findByTag(String tag) {

        List<Question> allQuestions = this.retrieveQuestions();
        List<Question> filteredQuestions = new ArrayList<>();

        for(Question question: allQuestions){
            for(Tag currentTag:question.getQuestionTags()){
                if(currentTag.getTitle().equals(tag)){
                    filteredQuestions.add(question);
                }
            }
        }

        return filteredQuestions;
    }
}
