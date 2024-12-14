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
        return baseMapper.selectQuestionTags().stream()
                .filter(questionTag -> !Objects.equals(questionTag.getQuestionId(), questionId))
                .map(a -> a.getTag().getTagName())
                .toList();
    }
}
