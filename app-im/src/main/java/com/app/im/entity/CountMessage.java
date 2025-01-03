package com.app.im.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class CountMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String uid;

    private Long likeOrCollectionCount;

    private Long commentCount;

    private Long followCount;
}
