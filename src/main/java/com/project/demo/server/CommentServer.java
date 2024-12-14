package com.project.demo.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.demo.entity.Comment;

import java.util.List;
import java.util.Map;

public interface CommentServer extends IService<Comment> {
    List<Integer> getQuestionIdByUserId(int userId);

    List<Integer> getQuestionIdByUserIdList(List<Integer> userIdList);

    Map<String, Integer> searchErrorInComment();

    Map<String, Integer> searchExceptionInComment();
}