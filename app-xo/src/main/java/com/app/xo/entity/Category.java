package com.app.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.app.common.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("t_category")
public class Category extends BaseEntity {

    private String title;

    private String pid;

    private String description;

    private long likeCount;

    private Integer sort;

    private String normalCover;

    private String hotCover;

}
