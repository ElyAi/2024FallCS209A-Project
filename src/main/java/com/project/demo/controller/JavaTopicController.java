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
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        // 降序排列
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        List<Map.Entry<String, Integer>> topList = new ArrayList<>();
        for (int i = 1; i < topN + 1 && i < list.size(); i++) {
            topList.add(list.get(i));
        }
        return topList;
    }
}
