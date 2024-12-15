package com.project.demo.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.demo.entity.Answer;

import java.util.List;
import java.util.Map;

public interface AnswerServer extends IService<Answer> {
    List<Integer> getQuestionIdByUserId(int userId);

    List<Integer> getQuestionIdByUserIdList(List<Integer> userIdList);

    List<Integer> getQuestionIdByAnswerId(int answerId);

    List<Integer> getQuestionIdByAnswerIdList(List<Integer> answerIdList);

    Map<String, Integer> searchErrorInAnswer();

    Map<String, Integer> searchExceptionInAnswer();

    List<Answer> getValidAnswer();

}
