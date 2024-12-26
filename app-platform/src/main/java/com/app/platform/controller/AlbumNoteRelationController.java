package com.app.platform.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.app.common.result.Result;
import com.app.platform.service.AlbumNoteRelationService;
import com.app.xo.vo.NoteSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/albumNoteRelation")
@RestController
public class AlbumNoteRelationController {

    @Autowired
    AlbumNoteRelationService albumNoteRelationService;

    /**
     * 得到当前专辑下的所有笔记
     *
     * @param currentPage 当前页
     * @param pageSize    分页数
     * @param albumId     专辑id
     * @param userId      用户id
     * @return 查询的笔记
     */
    @GetMapping("getNotePageByAid/{currentPage}/{pageSize}")
    public Result<?> getNotePageByAlbumId(@PathVariable long currentPage, @PathVariable long pageSize, String albumId, String userId) {
        Page<NoteSearchVo> pageInfo = albumNoteRelationService.getNotePageByAlbumId(currentPage, pageSize, albumId, userId);
        return Result.ok(pageInfo);
    }
}
