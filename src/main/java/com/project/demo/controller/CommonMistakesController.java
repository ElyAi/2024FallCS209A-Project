package com.project.demo.controller;

import com.project.demo.server.AnswerServer;
import com.project.demo.server.CommentServer;
import com.project.demo.server.QuestionServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/CommonMistakes")
public class CommonMistakesController {
    private final QuestionServer questionServer;
    private final AnswerServer answerServer;
    private final CommentServer commentServer;

    @Autowired
    public CommonMistakesController(QuestionServer questionServer,
                                    AnswerServer answerServer,
                                    CommentServer commentServer) {
        this.questionServer = questionServer;
        this.answerServer = answerServer;
        this.commentServer = commentServer;
    }

    @GetMapping("/getError")
    public List<Map.Entry<String, Integer>> getError(int topN) {
        Map<String, Integer> errorMap = new HashMap<String, Integer>();
        errorMap.putAll(questionServer.searchErrorInQuestion());
        errorMap.putAll(answerServer.searchErrorInAnswer());
        errorMap.putAll(commentServer.searchErrorInComment());

        return errorMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(topN)
                .toList();
    }

    @GetMapping("/getException")
    public List<Map.Entry<String, Integer>> getException(int topN) {
        Map<String, Integer> exceptionMap = new HashMap<String, Integer>();
        exceptionMap.putAll(questionServer.searchExceptionInQuestion());
        exceptionMap.putAll(answerServer.searchExceptionInAnswer());
        exceptionMap.putAll(commentServer.searchExceptionInComment());

        return exceptionMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(topN)
                .toList();
    }

}
