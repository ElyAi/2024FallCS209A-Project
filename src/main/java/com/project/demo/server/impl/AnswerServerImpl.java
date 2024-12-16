package com.project.demo.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.demo.entity.Answer;
import com.project.demo.entity.Question;
import com.project.demo.mapper.AnswerMapper;
import com.project.demo.server.AnswerServer;
import com.project.demo.server.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AnswerServerImpl extends ServiceImpl<AnswerMapper, Answer> implements AnswerServer {

    private final AnswerMapper answerMapper;

    public AnswerServerImpl(AnswerMapper answerMapper) {
        this.answerMapper = answerMapper;
    }

    @Override
    public List<Integer> getQuestionIdByUserId(int userId) {
        return baseMapper.selectListWithUserId(userId);
    }

    @Override
    public List<Integer> getQuestionIdByUserIdList(List<Integer> userIdList) {
        return userIdList.parallelStream()
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
        return answerIdList.parallelStream()
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
                String errorType = matcher.group().toLowerCase();
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
                String errorType = matcher.group().toLowerCase();
                exceptionMap.put(errorType, exceptionMap.getOrDefault(errorType, 0) + 1);
            }
        });

        return exceptionMap;
    }

    // 获取所有有效的回答即所回答的问题有回答被采用了或有高分回答
    @Override
    public List<Answer> getValidAnswer() {
        int highScoreRank = (int) (answerMapper.selectCount(null) / 100);

        List<Integer> questionIdListByAccepted = baseMapper.selectQuestionIdHasAcceptedAnswer();
        List<Integer> questionIdListByScore = baseMapper.selectQuestionIdHasHighScoreAnswer(highScoreRank);

        List<Integer> questionIdList = Stream.of(questionIdListByAccepted, questionIdListByScore)
                .flatMap(Collection::stream)
                .distinct()
                .toList();

        CopyOnWriteArrayList<Answer> validAnswersList = new CopyOnWriteArrayList<>();
        questionIdList.parallelStream().forEach(questionId -> {
            validAnswersList.addAll(baseMapper.selectByQuestionId(questionId));
        });
        return validAnswersList;
    }

}
