package com.project.demo.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.demo.entity.Answer;

import java.util.List;

public interface AnswerServer extends IService<Answer> {
    List<Answer> getAnswersByQuestionId(int questionId);

    List<Answer> getAnswersByUserId(int userId);

    List<Answer> getAnswersByUserIdList(List<Integer> userIdList);
}
