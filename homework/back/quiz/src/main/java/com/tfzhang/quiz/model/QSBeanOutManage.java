package com.tfzhang.quiz.model;

import java.util.List;

public class QSBeanOutManage {
    private String questionText;
    private List<String> options;
    private String answer;

    public QSBeanOutManage() {
    }

    public QSBeanOutManage(String questionText, List<String> options, String answer) {
        this.questionText = questionText;
        this.options = options;
        this.answer = answer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "QSBeanOutManage{" +
                "questionText='" + questionText + '\'' +
                ", options=" + options +
                ", answer='" + answer + '\'' +
                '}';
    }
}
