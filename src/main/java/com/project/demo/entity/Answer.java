package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("answers")
public class Answer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    @TableId(type = IdType.NONE)
    private Integer answerId;
    private Boolean isAccepted;
    private Integer score;
    private Timestamp communityOwnedDate;
    private Timestamp lastActivityDate;
    private Timestamp creationDate;
    private Timestamp lastEditDate;
    private Integer questionId;
    private Integer ownerId;
    private String body;
}
