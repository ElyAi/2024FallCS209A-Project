package com.project.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.demo.entity.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AnswerMapper extends BaseMapper<Answer> {
    @Select("SELECT * FROM answers WHERE question_id = #{questionId}")
    List<Answer> selectListWithQuestionId(@Param("questionId") int questionId);

    @Select("SELECT * FROM answers WHERE owner_id = #{userId}")
    List<Answer> selectListWithUserId(@Param("userId") int userId);

    @Select("SELECT * FROM answers WHERE owner_id = ANY(#{userIdList})")
    List<Answer> selectListByUserIdList(@Param("userIdList") List<Integer> userIdList);
}
