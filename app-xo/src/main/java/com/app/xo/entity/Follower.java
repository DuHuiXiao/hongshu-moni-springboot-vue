package com.app.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.app.common.entity.BaseEntity;
import lombok.Data;


@Data
@TableName("t_follower")
public class Follower extends BaseEntity {

    private String uid;

    private String fid;

}
