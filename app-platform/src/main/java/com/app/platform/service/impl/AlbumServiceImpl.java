package com.app.platform.service.impl;

import com.app.platform.service.AlbumService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.app.common.utils.ConvertUtils;
import com.app.platform.service.UserService;
import com.app.xo.dao.AlbumDao;
import com.app.xo.dto.AlbumDTO;
import com.app.xo.entity.Album;
import com.app.xo.entity.User;
import com.app.xo.vo.AlbumVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class  AlbumServiceImpl extends ServiceImpl<AlbumDao, Album> implements AlbumService {

    @Autowired
    UserService userService;

    @Override
    public Page<Album> getAlbumPageByUserId(long currentPage, long pageSize, String userId) {
        return this.page(new Page<>(currentPage, pageSize), new QueryWrapper<Album>().eq("uid", userId).orderByDesc("update_date"));
    }

    @Override
    public void saveAlbumByDTO(AlbumDTO albumDTO) {
        Album album = ConvertUtils.sourceToTarget(albumDTO, Album.class);
        this.save(album);
    }

    @Override
    public AlbumVo getAlbumById(String id) {
        Album album = this.getById(id);
        AlbumVo albumVo = ConvertUtils.sourceToTarget(album, AlbumVo.class);
        User user = userService.getById(album.getUid());
        albumVo.setUsername(user.getUsername());
        albumVo.setAvatar(user.getAvatar());
        return albumVo;
    }


    //TODO 删除专辑
    @Override
    public void deleteAlbumById(String id) {

    }

    @Override
    public void updateAlbumByDTO(AlbumDTO albumDTO) {
        Album album = ConvertUtils.sourceToTarget(albumDTO, Album.class);
        this.updateById(album);
    }
}
