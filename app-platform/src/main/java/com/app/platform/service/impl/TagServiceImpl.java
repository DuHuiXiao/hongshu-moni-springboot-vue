package com.app.platform.service.impl;

import com.app.platform.service.TagService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.app.xo.dao.TagDao;
import com.app.xo.entity.Tag;
import com.app.xo.vo.NoteVo;
import com.app.xo.vo.TagVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TagServiceImpl extends ServiceImpl<TagDao, Tag> implements TagService {
    @Override
    public List<TagVo> getHotTagList() {
        return null;
    }

    @Override
    public Page<NoteVo> getNotePageByTagId(long currentPage, long pageSize, String tagId, Integer type) {
        return null;
    }

    @Override
    public Page<Tag> getPageTagByKeyword(long currentPage, long pageSize, String keyword) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(keyword)) {
            queryWrapper.like("title", keyword);
        }
        queryWrapper.orderByDesc("like_count");
        return this.page(new Page<Tag>((int) currentPage, (int) pageSize), queryWrapper);
    }
}
