package com.ljy.quiz_backend.service;

import com.ljy.quiz_backend.model.question.QSBean;
import com.ljy.quiz_backend.model.question.QSBeanOut;
import com.ljy.quiz_backend.model.question.QSBeanOutManage;
import com.ljy.quiz_backend.model.question.QSBeanPage;

import java.util.List;


public interface QuestionService {
    public int insertQuestion(QSBean qsBean);
    public boolean delQuestion(Integer id);
    public List<QSBeanOut> getQuestions();
    public QSBeanPage page(Integer page, Integer pageSize);
    public  List<QSBeanOutManage> findByName(String keyword);


}
