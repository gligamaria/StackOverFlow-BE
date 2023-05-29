package com.example.demo.dto;

public class AnswerVoteDTO {

    private Long answerVoteId;
    private Integer voteType;

    public AnswerVoteDTO(Long questionVoteId, Integer voteType) {
        this.answerVoteId = questionVoteId;
        this.voteType = voteType;
    }

    public Long getAnswerVoteId() {
        return answerVoteId;
    }

    public void setAnswerVoteId(Long answerVoteId) {
        this.answerVoteId = answerVoteId;
    }

    public Integer getVoteType() {
        return voteType;
    }

    public void setVoteType(Integer voteType) {
        this.voteType = voteType;
    }
}
