package com.project.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.demo.entity.Questions;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionsMapper extends BaseMapper<Questions> {
}
