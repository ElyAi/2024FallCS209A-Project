package com.project.demo.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.demo.entity.Question;
import com.project.demo.mapper.QuestionMapper;
import com.project.demo.server.QuestionServer;
import com.project.demo.server.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServerImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionServer {

    @Autowired
    private UserServer userServer;

    @Override
    public int getTopicCount(String topic) {
        return 0;
    }
}