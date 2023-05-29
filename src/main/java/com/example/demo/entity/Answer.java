package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "answers")
@Getter
@Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answerId;

    @ManyToOne
    @JoinColumn(name="question_id", nullable=false)
    @JsonIgnore
    private Question question;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "answer_text")
    private String text;

    @Column(name = "picture")
    private String picture;

    @Column(name = "score")
    private Integer score;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @JsonIgnore
    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AnswerVote> answerVotes;

}
