package com.tfzhang.quiz;

import com.tfzhang.quiz.mapper.QuestionMapper;
import com.tfzhang.quiz.mapper.UserMapper;
import com.tfzhang.quiz.model.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.tfzhang.quiz.model.User;

import java.util.Date;
import java.util.List;

@SpringBootTest
class QuizApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSaveUser(){
        User user = new User();
        user.setUserName("testuser");
        user.setUserPassword("password123");

        user.setUserRole(0);
        user.setIsDelete(0);

        Date now = new Date();
        user.setCreateTime(now);
        user.setUpdateTime(now);

        // 执行插入操作,插入后返回插入成功的行数，这里默认就应该是1；
        int result = -1;
        result = userMapper.saveUser(user);
        System.out.println(result);
        //生成的id会默认回填到user对象中；
        System.out.println(user.getId());
    }

    @Test
    public void testExistsByName(){
        String name = "tons";
        int result = userMapper.existsByName(name);
        System.out.println(result);
    }

    @Test
    public void testDeleteUserById(){
        Long id = 1L;
        int result = userMapper.deleteUserById(id);
        System.out.println(result);
    }

    @Test
    public void testDeleteUserByUsername(){
        String username = "tons";
        int result = userMapper.deleteByUsername(username);
        System.out.println(result);
    }

    @Autowired
    private QuestionMapper questionMapper;

    @Test
    public void testInsertQuestion(){
        Question question = new Question();
        question.setQuestionText("What is the capital of France?");

        question.setAnswer1Text("Paris");
        question.setAnswer1Correct(true);

        question.setAnswer2Text("London");
        question.setAnswer2Correct(false);

        question.setAnswer3Text("Berlin");
        question.setAnswer3Correct(false);

        question.setAnswer4Text("Madrid");
        question.setAnswer4Correct(false);

        question.setIsDelete(0);
        question.setCreateTime(new Date());
        question.setUpdateTime(new Date());

        // 执行插入操作
        int result = questionMapper.insertQuestion(question);
        System.out.println(result);
        System.out.println(question.getId());
    }
    @Test
    public void testQuestionList(){
        List<Question> qlist = questionMapper.getQuestions();
        for (Question q : qlist) {
            System.out.println(q);
        }
    }

}
