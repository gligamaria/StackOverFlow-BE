package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "e_mail")
    private String email;

    @Column(name = "user_score")
    private Float userScore;

    //0 for not banned, 1 for banned
    @Column(name = "banned_status")
    private Integer bannedStatus;

    //0 for admin, 1 for normal user
    @Column(name = "user_type")
    private Integer userType;

    @Column(name = "encrypted_password")
    private String encryptedPassword;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Question> questions;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Answer> answers;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<AnswerVote> answerVotes;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<QuestionVote> questionVotes;

    public User(Long userId, String name, String email, Float userScore, Integer userType, String encryptedPassword, Set<Question> questions, Set<Answer> answers, Set<AnswerVote> answerVotes, Set<QuestionVote> questionVotes) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.userScore = userScore;
        this.userType = userType;
        this.encryptedPassword = encryptedPassword;
        this.questions = questions;
        this.answers = answers;
        this.answerVotes = answerVotes;
        this.questionVotes = questionVotes;
    }

    public User(Long userId, String name, String email, Float userScore, Integer bannedStatus, Integer userType, String encryptedPassword) {
        this.name = name;
        this.email = email;
        this.userScore = userScore;
        this.bannedStatus = bannedStatus;
        this.userType = userType;
        this.encryptedPassword = encryptedPassword;
    }

    public User() {

    }

}
