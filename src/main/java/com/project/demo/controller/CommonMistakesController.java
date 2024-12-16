package com.project.demo.controller;

import com.project.demo.exception.*;
import com.project.demo.server.AnswerServer;
import com.project.demo.server.CommentServer;
import com.project.demo.server.QuestionServer;
import com.project.demo.server.QuestionTagServer;
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
    private final QuestionTagServer questionTagServer;

    @Autowired
    public CommonMistakesController(QuestionServer questionServer,
                                    AnswerServer answerServer,
                                    CommentServer commentServer,
                                    QuestionTagServer questionTagServer) {
        this.questionServer = questionServer;
        this.answerServer = answerServer;
        this.commentServer = commentServer;
        this.questionTagServer = questionTagServer;
    }

    @GetMapping("/getTopNError")
    public List<Map.Entry<String, Integer>> getError(int topN) {
        if (topN <= 0) {
            throw new BadRequestException("传入topN不能小于或等于0");
        }
        Map<String, Integer> errorMap = new HashMap<String, Integer>();
        errorMap.putAll(questionServer.searchErrorInQuestion());
        errorMap.putAll(answerServer.searchErrorInAnswer());
        errorMap.putAll(commentServer.searchErrorInComment());
        errorMap.putAll(questionTagServer.searchErrorTopicMap());


        return errorMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(topN)
                .toList();
    }

    @GetMapping("/getTopNException")
    public List<Map.Entry<String, Integer>> getException(int topN) {
        if (topN <= 0) {
            throw new BadRequestException("传入topN不能小于或等于0");
        }
        Map<String, Integer> exceptionMap = new HashMap<String, Integer>();
        exceptionMap.putAll(questionServer.searchExceptionInQuestion());
        exceptionMap.putAll(answerServer.searchExceptionInAnswer());
        exceptionMap.putAll(commentServer.searchExceptionInComment());
        exceptionMap.putAll(questionTagServer.searchExceptionTopicMap());


        return exceptionMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(topN)
                .toList();
    }

    @GetMapping("/getSpecificError")
    public Integer getSpecificError(String errorName) {
        if (errorName == null || errorName.trim().isEmpty()) {
            throw new BadRequestException("errorName不能为空");
        }

        Map<String, Integer> errorMap = new HashMap<String, Integer>();
        errorMap.putAll(questionServer.searchErrorInQuestion());
        errorMap.putAll(answerServer.searchErrorInAnswer());
        errorMap.putAll(commentServer.searchErrorInComment());
        errorMap.putAll(questionTagServer.searchErrorTopicMap());

        errorName = errorName.toLowerCase().trim();
        if (!errorMap.containsKey(errorName)) {
            throw new ResourceNotFoundException(errorName + "不存在");
        }

        return errorMap.get(errorName);
    }

    @GetMapping("/getSpecificException")
    public Integer getSpecificException(String exceptionName) {
        if (exceptionName == null || exceptionName.trim().isEmpty()) {
            throw new BadRequestException("exceptionName不能为空");
        }
        Map<String, Integer> exceptionMap = new HashMap<String, Integer>();
        exceptionMap.putAll(questionServer.searchExceptionInQuestion());
        exceptionMap.putAll(answerServer.searchExceptionInAnswer());
        exceptionMap.putAll(commentServer.searchExceptionInComment());
        exceptionMap.putAll(questionTagServer.searchExceptionTopicMap());

        exceptionName = exceptionName.toLowerCase().trim();
        if (!exceptionMap.containsKey(exceptionName)) {
            throw new ResourceNotFoundException(exceptionName + "不存在");
        }

        return exceptionMap.get(exceptionName);
    }

}
