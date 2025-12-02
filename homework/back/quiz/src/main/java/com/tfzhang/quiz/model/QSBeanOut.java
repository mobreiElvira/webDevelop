package com.tfzhang.quiz.model;

import java.util.List;

public class QSBeanOut{
    private String question;
    private
    List<AnsBean> answers;
    //setter.getter.


    public QSBeanOut() {
    }

    public QSBeanOut(String question, List<AnsBean> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<AnsBean> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnsBean> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "QSBeanOut{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                '}';
    }
}
