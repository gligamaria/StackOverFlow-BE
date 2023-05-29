package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping( "/users")
@CrossOrigin("http://localhost:4200")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping( "/getAll")
    @ResponseBody
    public List<UserDTO> retrieveUsers() { return userService.retrieveUsersDTO(); }

    @GetMapping("/getByIdDTO/{cnp}")
    @ResponseBody
    public UserDTO retrieveByIdDTO(@PathVariable Long cnp){
        return userService.retrieveUserByIdDTO(cnp);
    }

    @GetMapping("/getById/{cnp}")
    @ResponseBody
    public User retrieveById(@PathVariable Long cnp){
        return userService.retrieveUserById(cnp);
    }

    @GetMapping("/getByEmail/{email}")
    @ResponseBody
    public User retrieveByEmail(@PathVariable String email){
        return userService.retrieveByEmail(email);
    }

    @DeleteMapping("/deleteById/{cnp}")
    @ResponseBody
    public String deleteById(@PathVariable Long cnp){
        return userService.deleteById(cnp);
    }

    @PostMapping("/insertUser")
    @ResponseBody
    public User insertUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @PutMapping("/updateUser")
    @ResponseBody
    public User updateAnswer(@RequestBody User user){
        return userService.updateUser(user);
    }

}
