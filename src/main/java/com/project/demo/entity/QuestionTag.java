package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor //有参构造
@NoArgsConstructor  //无参构造
@Data      //get、set方法和重新toString方法
@Accessors(chain = true)
@TableName("question_tag")
public class QuestionTag implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @MppMultiId
    private Integer questionId;
    @MppMultiId
    private Integer tagId;
    @TableField(exist = false)
    private Tag tag;
}
