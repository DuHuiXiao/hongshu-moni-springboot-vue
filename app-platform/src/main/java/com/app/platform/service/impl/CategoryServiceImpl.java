package com.app.platform.service.impl;

import com.app.platform.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.app.common.utils.ConvertUtils;
import com.app.common.utils.TreeUtils;
import com.app.xo.dao.CategoryDao;
import com.app.xo.entity.Category;
import com.app.xo.vo.CategoryVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {
    @Override
    public List<CategoryVo> getCategoryTreeData() {
        List<Category> list = this.list(new QueryWrapper<Category>().orderByDesc("like_count"));
        List<CategoryVo> voList = ConvertUtils.sourceToTarget(list, CategoryVo.class);
        return TreeUtils.build(voList);
    }
}
