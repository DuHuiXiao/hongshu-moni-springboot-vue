package com.app.xo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.app.xo.entity.Comment;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CommentDao extends BaseMapper<Comment> {
}
