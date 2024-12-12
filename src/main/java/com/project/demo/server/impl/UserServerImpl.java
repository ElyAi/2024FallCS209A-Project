package com.project.demo.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.demo.entity.User;
import com.project.demo.mapper.UserMapper;
import com.project.demo.server.UserServer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServerImpl extends ServiceImpl<UserMapper, User> implements UserServer{

    @Override
    public List<User> getHighReputationUsersWithValue(int standardValue) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("reputation", standardValue); // ge表示大于等于（greater than or equal to）
        return this.list(queryWrapper); // 使用MyBatis-Plus的list方法执行查询
    }

    @Override
    public List<User> getHighReputationUsersWithRank(int standardValueRank) {
        // 子查询，获取声誉值最高的前standardValueRank个值
        String subQuery = "SELECT reputation FROM users ORDER BY reputation DESC LIMIT " + standardValueRank;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("reputation IN (" + subQuery + ")"); // apply方法用于执行复杂的查询条件
        return this.list(queryWrapper); // 使用MyBatis-Plus的list方法执行查询
    }
}
