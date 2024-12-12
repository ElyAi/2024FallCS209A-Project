package com.project.demo.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.demo.entity.Answer;
import com.project.demo.mapper.AnswerMapper;
import com.project.demo.server.AnswerServer;
import com.project.demo.server.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServerImpl extends ServiceImpl<AnswerMapper, Answer> implements AnswerServer {
    @Override
    public List<Answer> getAnswersByQuestionId(int questionId) {
        return baseMapper.selectListWithQuestionId(questionId);
    }

    @Override
    public List<Answer> getAnswersByUserId(int userId) {
        return baseMapper.selectListWithUserId(userId);
    }

    @Override
    public List<Answer> getAnswersByUserIdList(List<Integer> userIdList) {
        return baseMapper.selectListByUserIdList(userIdList);
    }

}
