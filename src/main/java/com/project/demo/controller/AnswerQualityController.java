package com.project.demo.controller;

import com.project.demo.entity.Answer;
import com.project.demo.exception.BadRequestException;
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

    @GetMapping("/getTimeInfluenceQualityWithTopK")
    public Double getTimeInfluenceQualityWithTopK(int k) {
        if (k <= 0){
            throw new BadRequestException("k应该大于0");
        }
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

        List<Integer> topKEarliestAnswerList = validAnswersList.stream()
                .collect(Collectors.groupingBy(
                        Answer::getQuestionId,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                answers -> {
                                    return answers.stream()
                                            .sorted(Comparator.comparing(Answer::getCreationDate))
                                            .limit(k)
                                            .map(Answer::getAnswerId)
                                            .collect(Collectors.toList());
                                }
                        )
                ))
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().stream()) // 将Map的值扁平化为一个流
                .toList(); // 收集为List

        long duplicateCount = highQualityAnswersList.stream()
                .filter(topKEarliestAnswerList::contains)
                .count();
        return (double) duplicateCount / highQualityAnswersList.size();
    }

    @GetMapping("/getTimeInfluenceQuality")
    public List<Map.Entry<String, Double>> getTimeInfluenceQuality() {
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

        Map<String, Double> resultMap = new HashMap<>();
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
        double result = (double) duplicateCount / highQualityAnswersList.size();
        resultMap.put("Top1", result);

        List<Integer> top2EarliestAnswerList = validAnswersList.stream()
                .collect(Collectors.groupingBy(
                        Answer::getQuestionId,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                answers -> {
                                    return answers.stream()
                                            .sorted(Comparator.comparing(Answer::getCreationDate))
                                            .limit(2)
                                            .map(Answer::getAnswerId)
                                            .collect(Collectors.toList());
                                }
                        )
                ))
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().stream()) // 将Map的值扁平化为一个流
                .toList(); // 收集为List

        duplicateCount = highQualityAnswersList.stream()
                .filter(top2EarliestAnswerList::contains)
                .count();
        result = (double) duplicateCount / highQualityAnswersList.size();
        resultMap.put("Top2", result);

        List<Integer> top3EarliestAnswerList = validAnswersList.stream()
                .collect(Collectors.groupingBy(
                        Answer::getQuestionId,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                answers -> {
                                    return answers.stream()
                                            .sorted(Comparator.comparing(Answer::getCreationDate))
                                            .limit(3)
                                            .map(Answer::getAnswerId)
                                            .collect(Collectors.toList());
                                }
                        )
                ))
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().stream()) // 将Map的值扁平化为一个流
                .toList(); // 收集为List

        duplicateCount = highQualityAnswersList.stream()
                .filter(top3EarliestAnswerList::contains)
                .count();
        result = (double) duplicateCount / highQualityAnswersList.size();
        resultMap.put("Top3", result);

        List<Integer> top5EarliestAnswerList = validAnswersList.stream()
                .collect(Collectors.groupingBy(
                        Answer::getQuestionId,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                answers -> {
                                    return answers.stream()
                                            .sorted(Comparator.comparing(Answer::getCreationDate))
                                            .limit(5)
                                            .map(Answer::getAnswerId)
                                            .collect(Collectors.toList());
                                }
                        )
                ))
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().stream()) // 将Map的值扁平化为一个流
                .toList(); // 收集为List

        duplicateCount = highQualityAnswersList.stream()
                .filter(top5EarliestAnswerList::contains)
                .count();
        result = (double) duplicateCount / highQualityAnswersList.size();
        resultMap.put("Top5", result);

        return resultMap.entrySet().stream().toList();
    }

    @GetMapping("/getReputationInfluenceQualityWithTopProportion")
    public Double getReputationInfluenceQualityWithTopProportion(double proportion) {
        if (proportion <= 0 || proportion >= 100){
            throw new BadRequestException("proportion应该在0-100之间");
        }
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
        // 前 proportion%
        double highestReputation = userMapper.getKRankReputation((int) (totalUserCount * proportion / 100));

        List<Answer> reputationAnswerList = validAnswersList.parallelStream()
                .filter(answer -> {
                    int reputation = userMapper.getReputationByUserId(answer.getOwnerId());
                    return reputation >= highestReputation;
                }).toList();

        return (double) reputationAnswerList.size() / validAnswersList.size();
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
        // 前 5%
        double higherReputation = userMapper.getKRankReputation((int) (totalUserCount / 20));
        // 前 10%
        double normalReputation = userMapper.getKRankReputation((int) (totalUserCount / 10));
        // 前 50%
        double halfReputation = userMapper.getKRankReputation((int) (totalUserCount / 2));

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
        reputationMap.put("0.05", percentage);

        reputationAnswerList = validAnswersList.parallelStream()
                .filter(answer -> {
                    int reputation = userMapper.getReputationByUserId(answer.getOwnerId());
                    return reputation >= normalReputation;
                }).toList();
        percentage = (double) reputationAnswerList.size() / validAnswersList.size();
        reputationMap.put("0.10", percentage);

        reputationAnswerList = validAnswersList.parallelStream()
                .filter(answer -> {
                    int reputation = userMapper.getReputationByUserId(answer.getOwnerId());
                    return reputation >= halfReputation;
                }).toList();
        percentage = (double) reputationAnswerList.size() / validAnswersList.size();
        reputationMap.put("0.50", percentage);

        return reputationMap.entrySet().stream().toList();
    }

    @GetMapping("/getBodyLengthInfluenceQualityWithTopK")
    public Double getBodyLengthInfluenceQualityWithTopK(int k) {
        if (k <= 0){
            throw new BadRequestException("k应该大于0");
        }
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
        List<Integer> topKLongestAnswerList = validAnswersList.stream()
                .collect(Collectors.groupingBy(
                        Answer::getQuestionId,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                answers -> {
                                    List<Answer> topKAnswers = answers.stream()
                                            .sorted(Comparator.comparing(answer -> answer.getBody().length()))
                                            .collect(Collectors.toList());
                                    Collections.reverse(topKAnswers);
                                    return topKAnswers.stream()
                                            .map(Answer::getAnswerId)
                                            .limit(k)
                                            .toList();
                                }
                        )
                ))
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().stream()) // 将Map的值扁平化为一个流
                .toList(); // 收集为List

        long duplicateCount = highQualityAnswersList.stream()
                .filter(topKLongestAnswerList::contains)
                .count();
        return (double) duplicateCount / highQualityAnswersList.size();
    }

    @GetMapping("/getBodyLengthInfluenceQuality")
    public List<Map.Entry<String, Double>> getBodyLengthInfluenceQuality() {
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


        Map<String, Double> resultMap = new HashMap<>();
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

        double result = (double) duplicateCount / highQualityAnswersList.size();
        resultMap.put("Top1", result);

        System.out.println(validAnswersList.size() / longestAnswerList.size());
        List<Integer> top2LongestAnswerList = validAnswersList.stream()
                .collect(Collectors.groupingBy(
                        Answer::getQuestionId,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                answers -> {
                                    List<Answer> top2Answers = answers.stream()
                                            .sorted(Comparator.comparing(answer -> answer.getBody().length()))
                                            .collect(Collectors.toList());
                                    Collections.reverse(top2Answers);
                                    return top2Answers.stream()
                                            .map(Answer::getAnswerId)
                                            .limit(2)
                                            .toList();
                                }
                        )
                ))
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().stream()) // 将Map的值扁平化为一个流
                .toList(); // 收集为List

        duplicateCount = highQualityAnswersList.stream()
                .filter(top2LongestAnswerList::contains)
                .count();
        result = (double) duplicateCount / highQualityAnswersList.size();
        resultMap.put("Top2", result);

        List<Integer> top3LongestAnswerList = validAnswersList.stream()
                .collect(Collectors.groupingBy(
                        Answer::getQuestionId,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                answers -> {
                                    List<Answer> top3Answers = answers.stream()
                                            .sorted(Comparator.comparing(answer -> answer.getBody().length()))
                                            .collect(Collectors.toList());
                                    Collections.reverse(top3Answers);
                                    return top3Answers.stream()
                                            .map(Answer::getAnswerId)
                                            .limit(3)
                                            .toList();
                                }
                        )
                ))
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().stream()) // 将Map的值扁平化为一个流
                .toList(); // 收集为List

        duplicateCount = highQualityAnswersList.stream()
                .filter(top3LongestAnswerList::contains)
                .count();
        result = (double) duplicateCount / highQualityAnswersList.size();
        resultMap.put("Top3", result);

        List<Integer> top5LongestAnswerList = validAnswersList.stream()
                .collect(Collectors.groupingBy(
                        Answer::getQuestionId,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                answers -> {
                                    List<Answer> top5Answers = answers.stream()
                                            .sorted(Comparator.comparing(answer -> answer.getBody().length()))
                                            .collect(Collectors.toList());
                                    Collections.reverse(top5Answers);
                                    return top5Answers.stream()
                                            .map(Answer::getAnswerId)
                                            .limit(5)
                                            .toList();
                                }
                        )
                ))
                .entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().stream()) // 将Map的值扁平化为一个流
                .toList(); // 收集为List

        duplicateCount = highQualityAnswersList.stream()
                .filter(top5LongestAnswerList::contains)
                .count();
        result = (double) duplicateCount / highQualityAnswersList.size();
        resultMap.put("Top5", result);

        return resultMap.entrySet().stream().toList();
    }

}
