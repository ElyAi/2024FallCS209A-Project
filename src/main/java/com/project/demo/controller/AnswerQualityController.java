package com.project.demo.controller;

import com.project.demo.entity.Answer;
import com.project.demo.mapper.AnswerMapper;
import com.project.demo.mapper.UserMapper;
import com.project.demo.server.AnswerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/AnswerQuality")
public class AnswerQualityController {
    private final AnswerServer answerServer;
    private final AnswerMapper answerMapper;
    private final UserMapper userMapper;

    @Autowired
    public AnswerQualityController(AnswerServer answerServer,
                                   AnswerMapper answerMapper,
                                   UserMapper userMapper) {
        this.answerServer = answerServer;
        this.answerMapper = answerMapper;
        this.userMapper = userMapper;
    }

    @GetMapping("/getTimeInfluenceQuality")
    public double getTimeInfluenceQuality() {
        // 获取answer高分标准
        double highScore = answerMapper.getKRankScore((int) (answerMapper.selectCount(null) / 100));
        // 将没有被接受也没有高分回答的question从有效集里剔除
        List<Answer> validAnswersList = answerServer.getValidAnswer();
        // 获得所有被接收的answerId
        List<Integer> acceptedAnswersList = answerMapper.selectAcceptedAnswer()
                .stream().map(Answer::getAnswerId).toList();
        // 获得所有高分answerId
        List<Integer> highScoreAnswersList = answerMapper.selectHighQualityAnswer(highScore)
                .stream().map(Answer::getAnswerId).toList();

        // 合并为高质量answerId集合
        List<Integer> highQualityAnswersList = Stream.of(acceptedAnswersList, highScoreAnswersList)
                .flatMap(Collection::stream)
                .distinct()
                .toList();

        List<Integer> earliestAnswerList = validAnswersList.stream()
                .collect(
                        Collectors.groupingBy(
                                Answer::getQuestionId,
                                Collectors.minBy(Comparator.comparing(Answer::getCreationDate))
                        )
                )
                .entrySet()
                .stream().map(a -> a.getValue().map(Answer::getAnswerId).orElse(null))
                .toList();

        long duplicateCount = highQualityAnswersList.stream()
                .filter(earliestAnswerList::contains)
                .count();

        return (double) duplicateCount / earliestAnswerList.size();
    }

    @GetMapping("/getReputationInfluenceQuality")
    public List<Map.Entry<String, Double>> getReputationInfluenceQuality() {
        // 获取answer高分标准
        double highScore = answerMapper.getKRankScore((int) (answerMapper.selectCount(null) / 100));
        List<Answer> highScoreAnswersList = answerMapper.selectHighQualityAnswer(highScore);
        List<Answer> acceptedAnswersList = answerMapper.selectAcceptedAnswer();
        // 合并处理
        List<Answer> validAnswersList = Stream.of(highScoreAnswersList, acceptedAnswersList)
                .flatMap(Collection::stream)
                .distinct()
                .toList();
        validAnswersList = validAnswersList.stream().filter(answer -> answer.getOwnerId() != null).toList();
        long totalUserCount = userMapper.selectCount(null);
        // 前 1%
        double highestReputation = userMapper.getKRankReputation((int) (totalUserCount / 100));
        // 前 10%
        double higherReputation = userMapper.getKRankReputation((int) (totalUserCount / 10));
        // 前 50%
        double normalReputation = userMapper.getKRankReputation((int) (totalUserCount / 2));

        Map<String, Double> reputationMap = new HashMap<>();

        List<Answer> reputationAnswerList = validAnswersList.parallelStream()
                .filter(answer -> {
                    int reputation = userMapper.getReputationByUserId(answer.getOwnerId());
                    return reputation >= highestReputation;
                }).toList();
        double percentage = (double) reputationAnswerList.size() / validAnswersList.size();
        reputationMap.put("0.01", percentage);

        reputationAnswerList = validAnswersList.parallelStream()
                .filter(answer -> {
                    int reputation = userMapper.getReputationByUserId(answer.getOwnerId());
                    return reputation >= higherReputation;
                }).toList();
        percentage = (double) reputationAnswerList.size() / validAnswersList.size();
        reputationMap.put("0.10", percentage);

        reputationAnswerList = validAnswersList.parallelStream()
                .filter(answer -> {
                    int reputation = userMapper.getReputationByUserId(answer.getOwnerId());
                    return reputation >= normalReputation;
                }).toList();
        percentage = (double) reputationAnswerList.size() / validAnswersList.size();
        reputationMap.put("0.50", percentage);

        return reputationMap.entrySet().stream().toList();
    }

    @GetMapping("/getBodyLengthInfluenceQuality")
    public double getBodyLengthInfluenceQuality() {
        // 获取answer高分标准
        double highScore = answerMapper.getKRankScore((int) (answerMapper.selectCount(null) / 100));
        // 将没有被接受也没有高分回答的question从有效集里剔除
        List<Answer> validAnswersList = answerServer.getValidAnswer();
        // 获得所有被接收的answerId
        List<Integer> acceptedAnswersList = answerMapper.selectAcceptedAnswer()
                .stream().map(Answer::getAnswerId).toList();
        // 获得所有高分answerId
        List<Integer> highScoreAnswersList = answerMapper.selectHighQualityAnswer(highScore)
                .stream().map(Answer::getAnswerId).toList();

        // 合并为高质量answerId集合
        List<Integer> highQualityAnswersList = Stream.of(acceptedAnswersList, highScoreAnswersList)
                .flatMap(Collection::stream)
                .distinct()
                .toList();

        List<Integer> longestAnswerList = validAnswersList.stream()
                .collect(
                        Collectors.groupingBy(
                                Answer::getQuestionId,
                                Collectors.maxBy(Comparator.comparing(answer -> answer.getBody().length()))
                        )
                )
                .entrySet()
                .stream().map(a -> a.getValue().map(Answer::getAnswerId).orElse(null))
                .toList();

        long duplicateCount = highQualityAnswersList.stream()
                .filter(longestAnswerList::contains)
                .count();

        return (double) duplicateCount / longestAnswerList.size();
    }

}
