package com.app.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.app.xo.entity.Category;
import com.app.xo.vo.CategoryVo;

import java.util.List;


public interface CategoryService extends IService<Category> {
    /**
     * 获取树形分类数据
     *
     * @return 分类数据
     */
    List<CategoryVo> getCategoryTreeData();

}
