package com.project.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.demo.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface QuestionMapper extends BaseMapper<Question> {
    @Select("SELECT * " +
            "FROM questions " +
            "WHERE body LIKE #{keyword}")
    List<Question> searchQuestionsByKeywords(String keyword);

    @Select("SELECT question_id FROM questions WHERE owner_id=#{userId}")
    List<Integer> searchQuestionsByUserId(int userId);
}
