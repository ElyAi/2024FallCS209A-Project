package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

@AllArgsConstructor //有参构造
@NoArgsConstructor  //无参构造
@Data      //get、set方法和重新toString方法
@Accessors(chain = true)
public class Answers implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Integer answer_id;
    private Boolean is_accepted;
    private Integer score;
    private Timestamp community_owned_date;
    private Timestamp last_activity_date;
    private Timestamp creation_date;
    private Timestamp last_edit_date;
    private Integer question_id;
    private Integer owner_id;
    private String body;
}
