package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "question_text")
    private String text;

    @Column(name = "picture")
    private String picture;

    @Column(name = "score")
    private Integer score;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Answer> answers;

    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private Set<QuestionVote> questionVotes;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "question_tags",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> questionTags;

    public boolean deleteTag(String tag){

        Tag tagToRemove = null;
        for(Tag questionTag:this.questionTags){
            if(questionTag.getTitle().equals(tag)){
                tagToRemove = questionTag;
            }
        }
        if(tagToRemove != null){
            this.questionTags.remove(tagToRemove);
            return true;
        }
        return false;
    }

    public void addTag(Tag tag) {
        this.questionTags.add(tag);
    }

    public void addAnswer(Answer answer){ this.answers.add(answer); }

    public Set<Answer> filterAnswers() {
        Set<Answer> sortedAnswers = new TreeSet<>(Comparator.comparingInt(Answer::getScore)).descendingSet();;
        sortedAnswers.addAll(answers);
        return sortedAnswers;
    }

    public Question(User user, String title, String text, String picture, Integer score, LocalDateTime creationTime) {
        this.user = user;
        this.title = title;
        this.text = text;
        this.picture = picture;
        this.score = score;
        this.creationTime = creationTime;
    }

    public Question(){

    }
}

