package com.project.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.demo.entity.QuestionTag;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;


public interface QuestionTagMapper extends BaseMapper<QuestionTag> {
    @Select("select * from question_tag")
    @Results({
            @Result(column = "tag_id", property = "tagId"),
            @Result(column = "question_id", property = "questionId"),
            @Result(column = "tag_id", property = "tag", one = @One(select = "com.project.demo.mapper.TagMapper.selectById"))
    })
    List<QuestionTag> selectQuestionTags();

}
