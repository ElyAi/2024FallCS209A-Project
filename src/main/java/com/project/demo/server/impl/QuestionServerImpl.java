package com.project.demo.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.demo.entity.Question;
import com.project.demo.mapper.QuestionMapper;
import com.project.demo.server.QuestionServer;
import com.project.demo.server.UserServer;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.*;
import java.util.stream.Collectors;

@Service
public class QuestionServerImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionServer {

    @Override
    public Map<String, Integer> searchErrorInQuestion() {
        List<Question> questions = baseMapper.searchQuestionsByKeywords("%Error%");
        Pattern pattern = Pattern.compile("\\b[a-zA-Z]+Error\\b");
        Map<String, Integer> errorMap = new HashMap<>();

        questions.forEach(question -> {
            Matcher matcher = pattern.matcher(question.getBody());
            while (matcher.find()) {
                String errorType = matcher.group();
                errorMap.put(errorType, errorMap.getOrDefault(errorType, 0) + 1);
            }
        });

        return errorMap;
    }

    @Override
    public Map<String, Integer> searchExceptionInQuestion() {
        List<Question> questions = baseMapper.searchQuestionsByKeywords("%Exception%");
        Pattern pattern = Pattern.compile("\\b[a-zA-Z]+Exception\\b");
        Map<String, Integer> exceptionMap = new HashMap<>();

        questions.forEach(question -> {
            Matcher matcher = pattern.matcher(question.getBody());
            while (matcher.find()) {
                String errorType = matcher.group();
                exceptionMap.put(errorType, exceptionMap.getOrDefault(errorType, 0) + 1);
            }
        });

        return exceptionMap;
    }

    @Override
    public List<Integer> getQuestionIdByUserId(int userId) {
        return baseMapper.searchQuestionsByUserId(userId);
    }

    @Override
    public List<Integer> getQuestionIdByUserIdList(List<Integer> userIdList) {
        return userIdList.parallelStream()
                .map(this::getQuestionIdByUserId)
                .flatMap(List::stream)
                .toList();
    }
}