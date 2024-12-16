package com.project.demo.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.demo.entity.QuestionTag;
import com.project.demo.entity.Tag;
import com.project.demo.mapper.QuestionTagMapper;
import com.project.demo.server.QuestionTagServer;
import com.project.demo.server.TagServer;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class QuestionTagImpl extends ServiceImpl<QuestionTagMapper, QuestionTag> implements QuestionTagServer {
    @Autowired
    private TagServer tagServer;

    @Override
    public Map<String, Integer> searchTopicMap() {
        List<QuestionTag> questionTags = baseMapper.selectQuestionTags();
        Map<String, Integer> map = new HashMap<>();

        for (QuestionTag questionTag : questionTags) {
            map.put(questionTag.getTag().getTagName(), map.getOrDefault(questionTag.getTag().getTagName(), 0) + 1);
        }

        return map;
    }

    @Override
    public List<String> searchTopicByQuestionId(int questionId) {
        return baseMapper.selectQuestionTagsWithQuestionId(questionId).stream()
                .map(a -> a.getTag().getTagName())
                .toList();
    }

    @Override
    public Map<String, Integer> searchErrorTopicMap() {
        Map<String, Integer> errorTopicMap = new HashMap<>();
        List<QuestionTag> questionTags = baseMapper.selectQuestionTags();
        for (QuestionTag questionTag : questionTags) {
            if (questionTag.getTag().getTagName().contains("error")) {
                if(questionTag.getTag().getTagName().equals("error-handling")) continue;
                errorTopicMap.put(questionTag.getTag().getTagName(), errorTopicMap.getOrDefault(questionTag.getTag().getTagName(), 0) + 1);
            }
        }
        return errorTopicMap;
    }

    @Override
    public Map<String, Integer> searchExceptionTopicMap() {
        Map<String, Integer> exceptionTopicMap = new HashMap<>();
        List<QuestionTag> questionTags = baseMapper.selectQuestionTags();
        for (QuestionTag questionTag : questionTags) {
            if (questionTag.getTag().getTagName().contains("exception")) {
                if(questionTag.getTag().getTagName().equals("exception")) continue;
                if(questionTag.getTag().getTagName().contains("-")) continue;

                exceptionTopicMap.put(questionTag.getTag().getTagName(), exceptionTopicMap.getOrDefault(questionTag.getTag().getTagName(), 0) + 1);
            }
        }
        return exceptionTopicMap;
    }
}
