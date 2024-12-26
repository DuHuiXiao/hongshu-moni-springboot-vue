package com.app.platform.controller;

import com.app.platform.service.AlbumService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.app.common.result.Result;
import com.app.common.validator.ValidatorUtils;
import com.app.common.validator.group.AddGroup;
import com.app.common.validator.group.UpdateGroup;
import com.app.xo.dto.AlbumDTO;
import com.app.xo.entity.Album;
import com.app.xo.vo.AlbumVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/album")
@RestController
public class AlbumController {

    @Autowired
    AlbumService albumService;

    /**
     * 根据用户id获取专辑
     *
     * @param currentPage 当前页
     * @param pageSize    分页数
     * @param userId      用户id
     * @return 专辑数
     */
    @GetMapping("getAlbumPageByUserId/{currentPage}/{pageSize}")
    public Result<?> getAlbumPageByUserId(@PathVariable long currentPage, @PathVariable long pageSize, String userId) {
        Page<Album> page = albumService.getAlbumPageByUserId(currentPage, pageSize, userId);
        return Result.ok(page);
    }

    /**
     * 保存专辑
     *
     * @param albumDTO 专辑实体
     * @return success
     */
    @PostMapping("saveAlbumByDTO")
    public Result<?> saveAlbumByDTO(@RequestBody AlbumDTO albumDTO) {
        ValidatorUtils.validateEntity(albumDTO, AddGroup.class);
        albumService.saveAlbumByDTO(albumDTO);
        return Result.ok();
    }


    /**
     * 根据专辑id获取专辑
     *
     * @param albumId 专辑id
     * @return 专辑实体
     */
    @GetMapping("getAlbumById")
    public Result<?> getAlbumById(String albumId) {
        AlbumVo albumVo = albumService.getAlbumById(albumId);
        return Result.ok(albumVo);
    }

    /**
     * 根据专辑id删除专辑
     *
     * @param albumId 专辑id
     * @return success
     */
    @GetMapping("deleteAlbumById")
    public Result<?> deleteAlbumById(String albumId) {
        albumService.deleteAlbumById(albumId);
        return Result.ok();
    }

    /**
     * 更新专辑
     *
     * @param albumDTO 专辑实体
     * @return success
     */
    @PostMapping("updateAlbumByDTO")
    public Result<?> updateAlbumByDTO(@RequestBody AlbumDTO albumDTO) {
        ValidatorUtils.validateEntity(albumDTO, UpdateGroup.class);
        albumService.updateAlbumByDTO(albumDTO);
        return Result.ok();
    }
}
