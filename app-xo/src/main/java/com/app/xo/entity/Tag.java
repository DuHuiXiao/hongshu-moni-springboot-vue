package com.app.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.app.common.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("t_tag")
public class Tag extends BaseEntity {

    private Long likeCount;

    private String title;

    private Integer sort;
}
