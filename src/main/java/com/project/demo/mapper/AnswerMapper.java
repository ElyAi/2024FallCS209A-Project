package com.project.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.demo.entity.Answer;
import com.project.demo.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface AnswerMapper extends BaseMapper<Answer> {
    @Select("SELECT question_id FROM answers WHERE owner_id = #{userId}")
    List<Integer> selectListWithUserId(int userId);

    @Select("SELECT question_id FROM answers WHERE answer_id= #{answerId}")
    List<Integer> selectListWithAnswerId(int answerId);

    @Select("SELECT * " +
            "FROM answers " +
            "WHERE body LIKE #{keyword}")
    List<Answer> searchAnswersByKeywords(String keyword);

//    @Select("SELECT * " +
//            "FROM answers " +
//            "WHERE body LIKE #{keyword}")
//    List<Answer>

}
