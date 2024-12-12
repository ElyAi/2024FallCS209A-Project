package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

@AllArgsConstructor //有参构造
@NoArgsConstructor  //无参构造
@Data      //get、set方法和重新toString方法
@Accessors(chain = true)
@TableName("questions")
public class Question implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @NonNull
    private Integer question_id;
    private String title;
    private String link;
    private Boolean is_answered;
    private Integer view_count;
    private Integer accepted_answer_id;
    private Integer answer_count;
    private Integer score;
    private Timestamp last_activity_date;
    private Timestamp creation_date;
    private Timestamp last_edit_date;
    private Integer owner_id;
    private String text;
}
