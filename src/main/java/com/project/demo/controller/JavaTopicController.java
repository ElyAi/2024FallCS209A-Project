package com.project.demo.controller;

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

    @GetMapping("/getTopTopic")
    public List<Map.Entry<String, Integer>> getTopTopic(int topN) {
        Map<String, Integer> map = questionTagServer.searchTopicMap();
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(topN)
                .toList();
    }
}
