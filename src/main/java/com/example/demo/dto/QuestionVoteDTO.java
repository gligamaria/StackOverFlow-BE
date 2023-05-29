package com.example.demo.dto;

public class QuestionVoteDTO {

    private Long questionVoteId;
    private Integer voteType;

    public QuestionVoteDTO(Long questionVoteId, Integer voteType) {
        this.questionVoteId = questionVoteId;
        this.voteType = voteType;
    }

    public Long getQuestionVoteId() {
        return questionVoteId;
    }

    public void setQuestionVoteId(Long questionVoteId) {
        this.questionVoteId = questionVoteId;
    }

    public Integer getVoteType() {
        return voteType;
    }

    public void setVoteType(Integer voteType) {
        this.voteType = voteType;
    }
}
