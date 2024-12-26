package com.app.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.app.common.entity.BaseEntity;
import lombok.Data;


@Data
@TableName("t_album_note_relation")
public class AlbumNoteRelation extends BaseEntity {

    private String aid;

    //笔记
    private String nid;

}
