package com.project.demo.controller;

import com.project.demo.server.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/UserEngagement")
public class UserEngagementController {
    private final QuestionServer questionServer;
    private final AnswerServer answerServer;
    private final CommentServer commentServer;
    private final UserServer userServer;
    private final QuestionTagServer questionTagServer;

    public UserEngagementController(QuestionServer questionServer,
                                    AnswerServer answerServer,
                                    CommentServer commentServer,
                                    UserServer userServer,
                                    QuestionTagServer questionTagServer) {
        this.questionServer = questionServer;
        this.answerServer = answerServer;
        this.commentServer = commentServer;
        this.userServer = userServer;
        this.questionTagServer = questionTagServer;
    }

    @GetMapping("/getHighQualityTopic")
    public List<Map.Entry<String, Integer>> getHighQuality(int topN) {
        List<Integer> userIdList = userServer.getHighReputationUsersWithAvg();
        System.out.println("userIdList get over");
        List<Integer> questionIdList1 = commentServer.getQuestionIdByUserIdList(userIdList);
        System.out.println("commentServer get over");
        List<Integer> questionIdList2 = answerServer.getQuestionIdByUserIdList(userIdList);
        System.out.println("answerServer get over");
        List<Integer> questionIdList3 = questionServer.getQuestionIdByUserIdList(userIdList);
        System.out.println("questionServer get over");

        // 对question_id进行计数记录
        Map<Integer, Long> questionIdCountMap = Stream.of(questionIdList1, questionIdList2, questionIdList3)
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())); // 分组并计数

        System.out.println("questionIdCountMap get over");
        // 记录结果--标签
        Map<String, Integer> highQualityTopic = new HashMap<>();
        for (Map.Entry<Integer, Long> map : questionIdCountMap.entrySet()) {
            List<String> topics = questionTagServer.searchTopicByQuestionId(map.getKey());
            for (String topic : topics) {
                for (int i = 0; i < map.getValue(); i++) {
                    highQualityTopic.put(topic, highQualityTopic.getOrDefault(topic, 0) + 1);
                }
            }
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(highQualityTopic.entrySet());
        // 降序排列
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        List<Map.Entry<String, Integer>> topList = new ArrayList<>();
        for (int i = 1; i < topN + 1 && i < list.size(); i++) {
            topList.add(list.get(i));
        }
        return topList;

    }

}
