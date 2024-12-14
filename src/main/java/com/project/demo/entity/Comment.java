package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@AllArgsConstructor //有参构造
@NoArgsConstructor  //无参构造
@Data      //get、set方法和重新toString方法
@Accessors(chain = true)
@TableName("comments")
public class Comment implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @NonNull
    private Integer commentId;
    private Integer score;
    private Timestamp creationDate;
    private Integer ownerId;
    private Integer replyToId;
    private Integer questionId;
    private Integer answerId;
    private Boolean edited;
    private String body;
    @TableField(exist = false)
    private Answer answer;
}
