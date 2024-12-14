package com.project.demo.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.demo.entity.Tag;

import java.util.List;

public interface TagServer extends IService<Tag> {
    String searchTagNameByTagId(int tagId);

    List<String> searchTagNameList(List<Integer> tagIdList);

}
