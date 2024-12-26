package com.app.xo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.app.xo.entity.Chat;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ChatDao extends BaseMapper<Chat> {
}
