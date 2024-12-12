package com.project.demo.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.demo.entity.Comment;

import java.util.List;

public interface CommentServer extends IService<Comment> {
    List<Comment> getCommentsByQuestionId(int questionId);

    List<Comment> getCommentsByAnswerId(int answerId);

    List<Comment> getCommentsByUserId(int userId);
}