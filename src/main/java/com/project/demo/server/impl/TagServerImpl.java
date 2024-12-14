package com.project.demo.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.demo.entity.Tag;
import com.project.demo.mapper.TagMapper;
import com.project.demo.server.TagServer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServerImpl extends ServiceImpl<TagMapper, Tag> implements TagServer {

    @Override
    public String searchTagNameByTagId(int tagId) {
        return baseMapper.selectById(tagId).getTagName();
    }

    @Override
    public List<String> searchTagNameList(List<Integer> tagIdList) {
        return tagIdList.stream().map(this::searchTagNameByTagId).toList();
    }


}
