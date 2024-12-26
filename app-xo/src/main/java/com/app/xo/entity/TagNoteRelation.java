package com.app.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.app.common.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("t_tag_note_relation")
public class TagNoteRelation extends BaseEntity {

    private String nid;

    private String tid;
}
