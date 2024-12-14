package com.project.demo.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.demo.entity.Question;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;
import java.util.Map;

public interface QuestionServer extends IService<Question> {
    Map<String, Integer> searchErrorInQuestion();

    Map<String, Integer> searchExceptionInQuestion();

    List<Integer> getQuestionIdByUserId(int userId);

    List<Integer> getQuestionIdByUserIdList(List<Integer> userIdList);
}
