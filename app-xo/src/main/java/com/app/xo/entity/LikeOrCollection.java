package com.app.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.app.common.entity.BaseEntity;
import lombok.Data;


@TableName("t_like_or_collection")
@Data
public class LikeOrCollection extends BaseEntity {
    //点赞的用户
    private String uid;

    private String likeOrCollectionId;

    private String publishUid;

    private Integer type;

    private long timestamp;
}
