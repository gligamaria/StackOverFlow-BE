package com.example.demo.service;

import com.example.demo.controller.AnswerController;
import com.example.demo.controller.UserController;
import com.example.demo.dto.AnswerVoteDTO;
import com.example.demo.entity.Answer;
import com.example.demo.entity.AnswerVote;
import com.example.demo.entity.User;
import com.example.demo.repository.AnswerRepository;
import com.example.demo.repository.AnswerVoteRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AnswerVoteService {

    @Autowired
    AnswerVoteRepository answerVoteRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    AnswerController answerController;

    @Autowired
    UserController userController;

    public List<AnswerVote> retrieveVotes() {
        return (List<AnswerVote>) answerVoteRepository.findAll();
    }

    public AnswerVoteDTO retrieveVoteById(Long id) {

        Optional<AnswerVote> vote = answerVoteRepository.findById(id);

        if(vote.isPresent()) {
            return new AnswerVoteDTO(vote.get().getVoteId(), vote.get().getVoteType());
        } else {
            return null;
        }
    }

    public String deleteById(Long id){
        try{
            answerVoteRepository.deleteById(id);
            return "Success";
        }
        catch (Exception e){
            e.printStackTrace();
            return "Failed";
        }
    }

    public AnswerVote saveVote2(AnswerVote vote){

        Answer answer = answerController.retrieveAnswerById(vote.getAnswer().getAnswerId());
        //the user which gave the vote
        User user = userController.retrieveById(vote.getUser().getUserId());
        List<AnswerVote> votes = retrieveVotes();
        boolean found = false;

        for(AnswerVote v:votes){
            if(v.getUser().equals(user) && v.getAnswer().equals(answer)){
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

    public AnswerVote saveVote(AnswerVote vote){

        Answer answer = answerController.retrieveAnswerById(vote.getAnswer().getAnswerId());
        //the user which gave the vote
        User user = userController.retrieveById(vote.getUser().getUserId());
        //the user which wrote the answer
        User author = answer.getUser();

        if(vote.getVoteType() == 1){
            author.setUserScore(author.getUserScore() + 5);
            answer.setScore(answer.getScore() + 1);
        }
        else {
            {   author.setUserScore((float) (author.getUserScore() - 2.5));
                answer.setScore(answer.getScore() - 1);
            }
        }
        userRepository.save(author);
        answerRepository.save(answer);

        vote.setUser(user);
        vote.setAnswer(answer);
        return answerVoteRepository.save(vote);
    }

    public AnswerVote updateVote(Long id, AnswerVote answerVote) {

        Optional<AnswerVote> vote = answerVoteRepository.findById(id);
        if(vote.isPresent()) {
            AnswerVote oldAnswerVote = vote.get();
            if(answerVote.getVoteType()!=null) {
                oldAnswerVote.setVoteType(answerVote.getVoteType());

                Answer answer = answerController.retrieveAnswerById(oldAnswerVote.getAnswer().getAnswerId());
                //the user which gave the vote
                User user = userController.retrieveById(oldAnswerVote.getUser().getUserId());
                //the user which wrote the answer
                User author = answer.getUser();

                if(oldAnswerVote.getVoteType() == 1){
                    author.setUserScore(author.getUserScore() + 5);
                    answer.setScore(answer.getScore() + 1);
                }
                else {
                    {   author.setUserScore((float) (author.getUserScore() - 2.5));
                        answer.setScore(answer.getScore() - 1);
                    }
                }
                userRepository.save(author);
                answerRepository.save(answer);

                return answerVoteRepository.save(oldAnswerVote);
            }
        }
        return null;
    }

    public AnswerVote getByUserAndAnswer(Long userId, Long answerId) {
        Answer answer = answerController.retrieveAnswerById(answerId);
        User user = userController.retrieveById(userId);
        List<AnswerVote> votes = retrieveVotes();

        for(AnswerVote v:votes){
            if (v.getUser().equals(user) && v.getAnswer().equals(answer)) {
                return v;
            }
        }

        return null;
    }
}
