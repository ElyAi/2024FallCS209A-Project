package com.project.demo.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.demo.entity.User;

import java.util.List;

public interface UserServer extends IService<User> {
    List<User> getHighReputationUsersWithValue(double standardValue);

    List<User> getHighReputationUsersWithRank(int standardValueRank);

    List<Integer> getHighReputationUsersWithAvg();
}
