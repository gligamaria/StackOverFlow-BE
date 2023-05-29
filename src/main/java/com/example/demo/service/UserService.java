package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Question;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> retrieveUsers() {
        return (List<User>) userRepository.findAll();
    }

    public List<UserDTO> retrieveUsersDTO(){
        List<User> users = (List<User>) userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        if(users.size() != 0){
            for(User user:users){
                userDTOS.add(new UserDTO(user.getName()));
            }
            return userDTOS;
        }
        else return null;
    }

    public UserDTO retrieveUserByIdDTO(Long cnp) {
        Optional<User> user = userRepository.findById(cnp);

        if(user.isPresent()) {
            return new UserDTO(user.get().getName());
        } else {
            return null;
        }
    }

    public User retrieveByEmail(String email){
        List<User> users = (List<User>) userRepository.findAll();
        for(User user:users){
            if(user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }

    public User retrieveUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Answer does not exist")
        );
    }

    public String deleteById(Long id){
        try{
            userRepository.deleteById(id);
            return "Success";
        }
        catch (Exception e){
            e.printStackTrace();
            return "Failed";
        }
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(User newUser) {
        Optional<User> user = userRepository.findById(newUser.getUserId());
        if(user.isPresent()) {

            User oldUser = user.get();
            if(newUser.getName()!=null) {
                oldUser.setName(newUser.getName());
            }
            if(newUser.getUserScore()!=null) {
                oldUser.setUserScore(newUser.getUserScore());
            }
            if(newUser.getBannedStatus()!=null) {
                oldUser.setBannedStatus(newUser.getBannedStatus());
            }
            return userRepository.save(oldUser);
        }
        return null;
    }
}
