package com.project.demo.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.demo.entity.Answer;
import com.project.demo.entity.Comment;
import com.project.demo.mapper.CommentMapper;
import com.project.demo.server.CommentServer;
import com.project.demo.server.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CommentServerImpl extends ServiceImpl<CommentMapper, Comment> implements CommentServer {

    @Override
    public List<Integer> getQuestionIdByUserId(int userId) {
        return baseMapper.getCommentsByUserId(userId).stream()
                .filter(a -> a.getAnswer() != null)
                .map(a -> a.getAnswer().getQuestionId())
                .toList();
    }

    @Override
    public List<Integer> getQuestionIdByUserIdList(List<Integer> userIdList) {
        return userIdList.parallelStream()
                .map(this::getQuestionIdByUserId)
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public Map<String, Integer> searchErrorInComment() {
        List<Comment> comments = baseMapper.searchCommentsByKeywords("%Error%");
        Pattern pattern = Pattern.compile("\\b[a-zA-Z]+Error\\b");
        Map<String, Integer> errorMap = new HashMap<>();

        comments.forEach(comment -> {
            Matcher matcher = pattern.matcher(comment.getBody());
            while (matcher.find()) {
                String errorType = matcher.group();
                errorMap.put(errorType, errorMap.getOrDefault(errorType, 0) + 1);
            }
        });

        return errorMap;
    }

    @Override
    public Map<String, Integer> searchExceptionInComment() {
        List<Comment> comments = baseMapper.searchCommentsByKeywords("%Exception%");
        Pattern pattern = Pattern.compile("\\b[a-zA-Z]+Exception\\b");
        Map<String, Integer> exceptionMap = new HashMap<>();

        comments.forEach(comment -> {
            Matcher matcher = pattern.matcher(comment.getBody());
            while (matcher.find()) {
                String errorType = matcher.group();
                exceptionMap.put(errorType, exceptionMap.getOrDefault(errorType, 0) + 1);
            }
        });

        return exceptionMap;
    }
}
