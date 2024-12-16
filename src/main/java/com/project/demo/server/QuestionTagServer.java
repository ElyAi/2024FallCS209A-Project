package com.project.demo.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.demo.entity.QuestionTag;
import com.project.demo.entity.Tag;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;
import java.util.Map;


public interface QuestionTagServer extends IService<QuestionTag> {
     public Map<String, Integer> searchTopicMap();

     public List<String> searchTopicByQuestionId(int questionId);

     public Map<String, Integer> searchErrorTopicMap();

     public Map<String, Integer> searchExceptionTopicMap();
}
