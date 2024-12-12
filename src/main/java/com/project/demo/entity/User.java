package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@AllArgsConstructor //有参构造
@NoArgsConstructor  //无参构造
@Data      //get、set方法和重新toString方法
@Accessors(chain = true)
public class User implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Integer account_id;
    private Integer user_id;
    private Integer reputation;
    private String user_type;
    private Integer accept_rate;
    private String display_name;
    private String link;
}