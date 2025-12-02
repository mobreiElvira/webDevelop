package com.tfzhang.quiz.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;

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
    private Date createTime;

    /*更新时间 */
    private Date updateTime;
    // Getters and Setters


    public Question() {
    }

    public Question(Integer id, String questionText, String answer1Text, Boolean answer1Correct, String answer2Text, Boolean answer2Correct, String answer3Text, Boolean answer3Correct, String answer4Text, Boolean answer4Correct, Integer isDelete, Date createTime, Date updateTime) {
        this.id = id;
        this.questionText = questionText;
        this.answer1Text = answer1Text;
        this.answer1Correct = answer1Correct;
        this.answer2Text = answer2Text;
        this.answer2Correct = answer2Correct;
        this.answer3Text = answer3Text;
        this.answer3Correct = answer3Correct;
        this.answer4Text = answer4Text;
        this.answer4Correct = answer4Correct;
        this.isDelete = isDelete;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", answer1Text='" + answer1Text + '\'' +
                ", answer1Correct=" + answer1Correct +
                ", answer2Text='" + answer2Text + '\'' +
                ", answer2Correct=" + answer2Correct +
                ", answer3Text='" + answer3Text + '\'' +
                ", answer3Correct=" + answer3Correct +
                ", answer4Text='" + answer4Text + '\'' +
                ", answer4Correct=" + answer4Correct +
                ", isDelete=" + isDelete +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
