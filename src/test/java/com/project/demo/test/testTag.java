package com.project.demo.test;

import com.project.demo.controller.UserEngagementController;
import com.project.demo.entity.QuestionTag;
import com.project.demo.entity.User;
import com.project.demo.server.*;
import org.antlr.v4.runtime.misc.Pair;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@SpringBootTest
@MapperScan("com.project.demo.mapper")
public class testTag {

    @Autowired
    private QuestionTagServer questionTagServer;

    @Autowired
    private UserServer userServer;

    @Autowired
    private QuestionServer questionServer;

    @Autowired
    private TagServer tagServer;

    @Autowired
    private AnswerServer answerServer;

    @Autowired
    private CommentServer commentServer;

    @Autowired
    private UserEngagementController userEngagementController;

    @Test
    public void testSelect() {
        System.out.println(userEngagementController.getHighQuality(5));
    }
}
