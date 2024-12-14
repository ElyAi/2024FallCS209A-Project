package com.project.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT AVG(reputation) FROM users")
    Double getAverageReputation();

    @Select("SELECT * FROM users ORDER BY reputation DESC LIMIT #{standardValueRank}")
    List<User> highReputationUsersByRank(int standardValueRank);

    @Select("SELECT * FROM users WHERE reputation >= #{standardValue} ORDER BY reputation DESC")
    List<User> highReputationUsersByValue(double standardValue);
}
