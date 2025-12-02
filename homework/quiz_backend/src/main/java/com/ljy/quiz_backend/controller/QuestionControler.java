package com.ljy.quiz_backend.controller;

import com.ljy.quiz_backend.model.question.QSBean;
import com.ljy.quiz_backend.model.question.QSBeanOut;
import com.ljy.quiz_backend.model.question.QSBeanOutManage;
import com.ljy.quiz_backend.model.question.QSBeanPage;
import com.ljy.quiz_backend.model.Result;
import com.ljy.quiz_backend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionControler {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/addQuestion")
    public Result addQuestion(@RequestBody QSBean question) {
        int result = questionService.insertQuestion(question);
        if (result > 0){
            return Result.success("插入新问题成功");
        }else{
            return Result.error("插入失败");
        }
    }
    @GetMapping("/delQuestion")
    public Result deleteQuestion(Integer id) {
        boolean success = questionService.delQuestion(id);
        if (success) {
            return Result.success("用户成功删除");
        }
        return Result.error("用户不存在或已被删除");
    }

    @GetMapping("/getQuestion")
    public Result getQuestion() {
        List<QSBeanOut> qsBeanOut = questionService.getQuestions();
        return Result.success(qsBeanOut);
    }

    @GetMapping("/questions")
    public Result getPage(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="5")Integer pageSize){
        QSBeanPage qsBeanPage=questionService.page(page, pageSize);
        return Result.success(qsBeanPage);
    }

    @GetMapping("/findQuestion")
    public Result getUser(String keyword){
        // 只可以查询题干
        List<QSBeanOutManage> qsBeanOut=questionService.findByName(keyword);
        return Result.success(qsBeanOut);
    }


}
