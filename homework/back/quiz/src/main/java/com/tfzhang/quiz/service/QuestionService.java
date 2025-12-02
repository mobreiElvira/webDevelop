package com.tfzhang.quiz.service;

import com.tfzhang.quiz.model.QSBean;
import com.tfzhang.quiz.model.QSBeanOut;
import com.tfzhang.quiz.model.QSBeanOutManage;
import com.tfzhang.quiz.model.QSBeanPage;

import java.util.List;

public interface QuestionService {
    public int insertQuestion(QSBean qsBean);

    public boolean delQuestion(Integer id);

    public List<QSBeanOut> getQuestions();

    public QSBeanPage page(Integer page, Integer pageSize);

    public  List<QSBeanOutManage> findByName(String keyword);
}
