package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.PasswordService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/users")
@CrossOrigin("http://localhost:4200")
public class AuthController {

    @Autowired
    UserService userService;
    PasswordService passwordService = new PasswordService();

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.retrieveByEmail(loginRequest.email);
        if(user!=null){
            if(user.getEncryptedPassword().equals(passwordService.encryptPassword(loginRequest.password))){
                if(user.getBannedStatus()!=1)
                    return ResponseEntity.ok(user);
                else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}