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
public class Comments implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Integer score;
    private Timestamp creation_date;
    private Integer owner_id;
    private Integer reply_to_id;
    private Integer question_id;
    private Integer answer_id;
    private Boolean edited;
    private String body;
}
