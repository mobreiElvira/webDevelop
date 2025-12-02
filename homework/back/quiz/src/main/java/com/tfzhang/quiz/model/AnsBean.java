package com.tfzhang.quiz.model;

public class AnsBean{
    private String text;
    private boolean correct;
    //类构造方法；
    public AnsBean(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
