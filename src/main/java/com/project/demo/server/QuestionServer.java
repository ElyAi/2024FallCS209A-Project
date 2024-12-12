package com.project.demo.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.demo.entity.Question;

public interface QuestionServer extends IService<Question> {
    int getTopicCount(String topic);

}
