package com.project.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class JdbcController {
    //SpringBoot帮我们整合了一个jdbc的模板框架，注册了模板的Bean，我们可以直接使用，使用@Autowired注解将JDBC模板自动装配进来即可
    @Autowired
    JdbcTemplate jdbcTemplate;

    //将jdbc模板导入后，就可以使用模板中的方法了
    @RequestMapping("/jdbc")
    public List<Map<String,Object>> userList() {
        //原生的jdbc还需要手动编写sql
        String sql = "select * from user";

        //调用模板中的查询方法，这个模板中有很多的增删改查的重载方法，按照我们的需求调用即可
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);

        //最后将查询到的值返回到浏览器
        return maps;

    }
}