package com.project.demo.controller;

import com.project.demo.server.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
    public List<Map.Entry<String, Long>> getHighQuality(int topN) {
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
        ConcurrentHashMap<String, Long> highQualityTopic = new ConcurrentHashMap<>();
        questionIdCountMap.entrySet().parallelStream().forEach(entry -> {
            List<String> topics = questionTagServer.searchTopicByQuestionId(entry.getKey());
            Long times = entry.getValue();
            for (String topic : topics) {
                if (topic == null) continue;
                highQualityTopic.compute(topic,
                        (key, value) -> (value == null) ? times : value + times);
            }
        });

//        for (Map.Entry<Integer, Long> map : questionIdCountMap.entrySet()) {
//            List<String> topics = questionTagServer.searchTopicByQuestionId(map.getKey());
//            for (String topic : topics) {
//                if (topic == null) continue;
//                Long value = map.getValue();
//                highQualityTopic.put(topic, highQualityTopic.getOrDefault(topic, 0L) + value);
//            }
//        }

        return highQualityTopic.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(topN)
                .toList();

    }

}
