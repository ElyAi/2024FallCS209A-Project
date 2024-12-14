package com.project.demo.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.demo.entity.Answer;
import com.project.demo.entity.Question;
import com.project.demo.mapper.AnswerMapper;
import com.project.demo.server.AnswerServer;
import com.project.demo.server.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class AnswerServerImpl extends ServiceImpl<AnswerMapper, Answer> implements AnswerServer {

    @Override
    public List<Integer> getQuestionIdByUserId(int userId) {
        return baseMapper.selectListWithUserId(userId);
    }

    @Override
    public List<Integer> getQuestionIdByUserIdList(List<Integer> userIdList) {
        return userIdList.stream()
                .map(this::getQuestionIdByUserId)
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public List<Integer> getQuestionIdByAnswerId(int answerId) {
        return baseMapper.selectListWithAnswerId(answerId);
    }

    @Override
    public List<Integer> getQuestionIdByAnswerIdList(List<Integer> answerIdList) {
        return answerIdList.stream()
                .map(this::getQuestionIdByAnswerId)
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public Map<String, Integer> searchErrorInAnswer() {
        List<Answer> answers = baseMapper.searchAnswersByKeywords("%Error%");
        Pattern pattern = Pattern.compile("\\b[a-zA-Z]+Error\\b");
        Map<String, Integer> errorMap = new HashMap<>();

        answers.forEach(answer -> {
            Matcher matcher = pattern.matcher(answer.getBody());
            while (matcher.find()) {
                String errorType = matcher.group();
                errorMap.put(errorType, errorMap.getOrDefault(errorType, 0) + 1);
            }
        });

        return errorMap;
    }

    @Override
    public Map<String, Integer> searchExceptionInAnswer() {
        List<Answer> answers = baseMapper.searchAnswersByKeywords("%Exception%");
        Pattern pattern = Pattern.compile("\\b[a-zA-Z]+Exception\\b");
        Map<String, Integer> exceptionMap = new HashMap<>();

        answers.forEach(answer -> {
            Matcher matcher = pattern.matcher(answer.getBody());
            while (matcher.find()) {
                String errorType = matcher.group();
                exceptionMap.put(errorType, exceptionMap.getOrDefault(errorType, 0) + 1);
            }
        });

        return exceptionMap;
    }

}
