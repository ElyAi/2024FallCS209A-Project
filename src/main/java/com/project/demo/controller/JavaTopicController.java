package com.project.demo.controller;

import com.project.demo.exception.*;
import com.project.demo.server.QuestionTagServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/JavaTopics")
public class JavaTopicController {
    private final QuestionTagServer questionTagServer;

    @Autowired
    public JavaTopicController(QuestionTagServer questionTagServer) {
        this.questionTagServer = questionTagServer;
    }

    @GetMapping("/getTopNTopic")
    public List<Map.Entry<String, Integer>> getTopTopic(int topN) {
        if (topN <= 0) {
            throw new BadRequestException("传入topN不能小于或等于0");
        }
        if (topN > 500) {
            throw new BadRequestException("传入topN不能大于500");
        }
        Map<String, Integer> map = questionTagServer.searchTopicMap();
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(topN)
                .toList();
    }

    @GetMapping("/getSpecificTopic")
    public int getSpecificTopic(String topicName) {
        if (topicName == null){
            throw new BadRequestException("topicName不能为空");
        }
        Map<String, Integer> map = questionTagServer.searchTopicMap();
        if (!map.containsKey(topicName)){
            throw new ResourceNotFoundException(topicName + "不存在");
        }
        return map.get(topicName);
    }
}
