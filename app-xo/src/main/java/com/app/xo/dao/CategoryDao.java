package com.app.xo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.app.xo.entity.Category;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CategoryDao extends BaseMapper<Category> {
}
