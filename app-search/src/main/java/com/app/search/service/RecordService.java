package com.app.search.service;

import com.app.xo.vo.RecordSearchVo;

import java.util.List;

public interface RecordService {
    /**
     * 获取搜索记录
     * @param keyword  关键词
     * @return 记录集合
     */
    List<RecordSearchVo> getRecordByKeyWord(String keyword);
    /**
     * 热门关键词
     * @return list
     */
    List<RecordSearchVo> getHotRecord();
    /**
     * 热门关键词
     * @return list
     */
    void addRecord(String keyword);
}
