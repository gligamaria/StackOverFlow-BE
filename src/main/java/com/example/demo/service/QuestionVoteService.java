package com.example.demo.service;

import com.example.demo.controller.QuestionController;
import com.example.demo.controller.UserController;
import com.example.demo.dto.QuestionVoteDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.AnswerRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.QuestionVoteRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuestionVoteService {

    @Autowired
    QuestionVoteRepository questionVoteRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionController questionController;

    @Autowired
    UserController userController;

    public List<QuestionVote> retrieveVotes() {
        return (List<QuestionVote>) questionVoteRepository.findAll();
    }

    public QuestionVoteDTO retrieveVoteById(Long id) {

        Optional<QuestionVote> vote = questionVoteRepository.findById(id);

        if(vote.isPresent()) {
            return new QuestionVoteDTO(vote.get().getVoteId(), vote.get().getVoteType());
        } else {
            return null;
        }
    }

    public String deleteById(Long id){
        try{
            questionVoteRepository.deleteById(id);
            return "Success";
        }
        catch (Exception e){
            e.printStackTrace();
            return "Failed";
        }
    }

    public QuestionVote saveVote2(QuestionVote vote){
        Question question = questionController.retrieveQuestionById(vote.getQuestion().getQuestionId());
        //the user which gave the vote
        User user = userController.retrieveById(vote.getUser().getUserId());
        List<QuestionVote> votes = retrieveVotes();
        boolean found = false;

        for(QuestionVote v:votes){
            if(v.getUser().equals(user) && v.getQuestion().equals(question)){
                //the vote exists
                found = true;
                if(!Objects.equals(v.getVoteType(), vote.getVoteType())){
                    return updateVote(v.getVoteId(), vote);
                }
            }
        }

        if(!found){
            return saveVote(vote);
        }

        return null;
    }

    public QuestionVote saveVote(QuestionVote vote){

        Question question = questionController.retrieveQuestionById(vote.getQuestion().getQuestionId());
        //user who gave the vote
        User user = userController.retrieveById(vote.getUser().getUserId());
        //user who wrote the question
        User author = question.getUser();

        if(vote.getVoteType() == 1){
            author.setUserScore((float) (author.getUserScore() + 2.5));
            question.setScore(question.getScore() + 1);
        }
        else {
            {
                author.setUserScore((float) (author.getUserScore() - 1.5));
                question.setScore(question.getScore() - 1);

                user.setUserScore((float) (user.getUserScore() - 1.5));
                userRepository.save(user);
            }
        }
        userRepository.save(author);
        questionRepository.save(question);

        vote.setUser(user);
        vote.setQuestion(question);
        return questionVoteRepository.save(vote);

    }

    public QuestionVote updateVote(Long id, QuestionVote questionVote) {

        Optional<QuestionVote> vote = questionVoteRepository.findById(id);
        if(vote.isPresent()) {
            QuestionVote oldQuestionVote = vote.get();
            if(questionVote.getVoteType()!=null) {
                oldQuestionVote.setVoteType(questionVote.getVoteType());
                Question question = questionController.retrieveQuestionById(oldQuestionVote.getQuestion().getQuestionId());
                User author = question.getUser();

                if(oldQuestionVote.getVoteType() == 1){
                    author.setUserScore(author.getUserScore() + 5);
                    question.setScore(question.getScore() + 1);
                }
                else {
                    {   author.setUserScore((float) (author.getUserScore() - 2.5));
                        question.setScore(question.getScore() - 1);
                    }
                }
                userRepository.save(author);
                questionRepository.save(question);

                return questionVoteRepository.save(oldQuestionVote);
            }
        }
        return null;
    }

    public QuestionVote getByUserAndQuestion(Long userId, Long questionId) {
        Question question = questionController.retrieveQuestionById(questionId);
        User user = userController.retrieveById(userId);
        List<QuestionVote> votes = retrieveVotes();

        for(QuestionVote v:votes){
            if (v.getUser().equals(user) && v.getQuestion().equals(question)) {
                return v;
            }
        }

        return null;
    }

}
