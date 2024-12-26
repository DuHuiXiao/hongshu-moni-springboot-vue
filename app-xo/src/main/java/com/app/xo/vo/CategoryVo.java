package com.app.xo.vo;

import com.app.common.utils.TreeNode;
import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryVo extends TreeNode<CategoryVo> implements Serializable {
    private String title;
    private long likeCount;
}
