package com.project.demo.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.demo.entity.User;
import com.project.demo.mapper.UserMapper;
import com.project.demo.server.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServerImpl extends ServiceImpl<UserMapper, User> implements UserServer{

    private final UserMapper userMapper;

    @Autowired
    public UserServerImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getHighReputationUsersWithValue(double standardValue) {
        return baseMapper.highReputationUsersByValue(standardValue);
    }

    @Override
    public List<User> getHighReputationUsersWithRank(int standardValueRank) {
        return baseMapper.highReputationUsersByRank(standardValueRank);
    }

    @Override
    public List<Integer> getHighReputationUsersWithAvg() {
        long highReputationRank = userMapper.selectCount(null) / 100;
        System.out.println(highReputationRank);
        return getHighReputationUsersWithRank((int) highReputationRank).stream()
                .map(User::getUserId).toList();
    }

}
