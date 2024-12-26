package com.app.xo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.app.xo.entity.Album;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AlbumDao extends BaseMapper<Album>{
}
