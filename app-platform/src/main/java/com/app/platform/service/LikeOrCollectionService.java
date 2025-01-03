package com.app.platform.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.app.xo.dto.LikeOrCollectionDTO;
import com.app.xo.entity.LikeOrCollection;
import com.app.xo.vo.LikeOrCollectionVo;


public interface LikeOrCollectionService extends IService<LikeOrCollection> {
    /**
     * 点赞或收藏
     *
     * @param likeOrCollectionDTO 点赞收藏实体
     * @return success
     */
    void likeOrCollectionByDTO(LikeOrCollectionDTO likeOrCollectionDTO);

    /**
     * 是否点赞或收藏
     *
     * @param likeOrCollectionDTO 点赞收藏实体
     * @return flag
     */
    boolean isLikeOrCollection(LikeOrCollectionDTO likeOrCollectionDTO);

    /**
     * 得到当前用户最新的点赞和收藏信息
     *
     * @param currentPage 当前页
     * @param pageSize    分页数
     * @return LikeOrCollectionVo
     */
    Page<LikeOrCollectionVo> getNoticeLikeOrCollection(long currentPage, long pageSize);
}
