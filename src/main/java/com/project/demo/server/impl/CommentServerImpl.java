package com.project.demo.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.demo.entity.Comment;
import com.project.demo.mapper.CommentMapper;
import com.project.demo.server.CommentServer;
import com.project.demo.server.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServerImpl extends ServiceImpl<CommentMapper, Comment> implements CommentServer {

    @Override
    public List<Comment> getCommentsByQuestionId(int questionId) {
        return List.of();
    }

    @Override
    public List<Comment> getCommentsByAnswerId(int answerId) {
        return List.of();
    }

    @Override
    public List<Comment> getCommentsByUserId(int userId) {
        return List.of();
    }
}
