package com.example.demo.service;

import com.example.demo.dto.AnswerDTO;
import com.example.demo.entity.Answer;
import com.example.demo.entity.Question;
import com.example.demo.entity.User;
import com.example.demo.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    public List<Answer> retrieveAnswer() {
        List<Answer> answers = (List<Answer>) answerRepository.findAll();
        answers.sort(Comparator.comparing(Answer::getScore));
        return answers;
    }

    public AnswerDTO retrieveAnswerByIdDTO(Long id) {

        Optional<Answer> answer = answerRepository.findById(id);

        if(answer.isPresent()) {
            return new AnswerDTO(answer.get().getText(), answer.get().getUser().getName());
        } else {
            return null;
        }

    }
    public Answer retrieveAnswerById(Long id) {
        return answerRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Answer does not exist")
        );
    }

    public List<Answer> retrieveByQuestionId(Long id) {

        Question question = questionService.retrieveQuestionById(id);
        List<Answer> allAnswers = retrieveAnswer();
        List<Answer> questionAnswers = new ArrayList<>();
        for(Answer answer:allAnswers){
            if(question.getAnswers().contains(answer)){
                questionAnswers.add(answer);
            }
//            if(Objects.equals(answer.getQuestion().getQuestionId(), id)){
//                questionAnswers.add(answer);
//            }
        }
        questionAnswers.sort(Comparator.comparing(Answer::getScore));
        Collections.reverse(questionAnswers);
        return  questionAnswers;

    }

    public String deleteById(Long id){
        try{
            answerRepository.deleteById(id);
            return "Success";
        }
        catch (Exception e){
            e.printStackTrace();
            return "Failed";
        }
    }

    public Answer saveAnswer(Answer answer, Long questionId){

        Question question = questionService.retrieveQuestionById(questionId);
        answer.setQuestion(question);
        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Long id, Answer newAnswer) {

        Optional<Answer> answer = answerRepository.findById(id);
        if(answer.isPresent()) {

            Answer oldAnswer = answer.get();
            // the user and the creation date&time attributes
            // should not change
            if(newAnswer.getText()!=null) {
                oldAnswer.setText(newAnswer.getText());
            }
            if(newAnswer.getPicture()!=null) {
                oldAnswer.setPicture(newAnswer.getPicture());
            }
            return answerRepository.save(oldAnswer);
        }
        return null;
    }
}
