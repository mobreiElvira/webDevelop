package com.ljy.quiz_backend.model.question;

import java.time.LocalDateTime;

public class Question {
    private Integer id;
    private String questionText;

    private String answer1Text;
    private Boolean answer1Correct;

    private String answer2Text;
    private Boolean answer2Correct;

    private String answer3Text;
    private Boolean answer3Correct;

    private String answer4Text;
    private Boolean answer4Correct;

    /* 是否删除 */
    private Integer isDelete;

    /* 创建时间 */
    private LocalDateTime createTime;

    /*更新时间 */
    private LocalDateTime updateTime;
    // Getters and Setters


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswer1Text() {
        return answer1Text;
    }

    public void setAnswer1Text(String answer1Text) {
        this.answer1Text = answer1Text;
    }

    public Boolean getAnswer1Correct() {
        return answer1Correct;
    }

    public void setAnswer1Correct(Boolean answer1Correct) {
        this.answer1Correct = answer1Correct;
    }

    public String getAnswer2Text() {
        return answer2Text;
    }

    public void setAnswer2Text(String answer2Text) {
        this.answer2Text = answer2Text;
    }

    public Boolean getAnswer2Correct() {
        return answer2Correct;
    }

    public void setAnswer2Correct(Boolean answer2Correct) {
        this.answer2Correct = answer2Correct;
    }

    public String getAnswer3Text() {
        return answer3Text;
    }

    public void setAnswer3Text(String answer3Text) {
        this.answer3Text = answer3Text;
    }

    public Boolean getAnswer3Correct() {
        return answer3Correct;
    }

    public void setAnswer3Correct(Boolean answer3Correct) {
        this.answer3Correct = answer3Correct;
    }

    public String getAnswer4Text() {
        return answer4Text;
    }

    public void setAnswer4Text(String answer4Text) {
        this.answer4Text = answer4Text;
    }

    public Boolean getAnswer4Correct() {
        return answer4Correct;
    }

    public void setAnswer4Correct(Boolean answer4Correct) {
        this.answer4Correct = answer4Correct;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
