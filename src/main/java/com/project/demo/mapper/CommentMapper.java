package com.project.demo.mapper;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.demo.entity.Answer;
import com.project.demo.entity.Comment;
import com.project.demo.entity.Question;
import lombok.NonNull;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;


public interface CommentMapper extends BaseMapper<Comment> {
    @Select("SELECT * FROM comments WHERE owner_id=#{userId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "comment_id", property = "commentId"),
            @Result(column = "creation_date", property = "creationDate"),
            @Result(column = "owner_id", property = "ownerId"),
            @Result(column = "reply_to_id", property = "replyToId"),
            @Result(column = "question_id", property = "questionId"),
            @Result(column = "answer_id", property = "answerId"),
            @Result(column = "edited", property = "edited"),
            @Result(column = "body", property = "body"),
            @Result(column = "answer_id", property = "answer", one = @One(select = "com.project.demo.mapper.AnswerMapper.selectById"))
    })
    List<Comment> getCommentsByUserId(int userId);

    @Select("SELECT * " +
            "FROM comments " +
            "WHERE body LIKE #{keyword}")
    List<Comment> searchCommentsByKeywords(String keyword);

}
