package com.app.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.app.platform.service.TagNoteRelationService;
import com.app.xo.dao.TagNoteRelationDao;
import com.app.xo.entity.TagNoteRelation;
import org.springframework.stereotype.Service;

@Service
public class TagNoteRelationServiceImpl extends ServiceImpl<TagNoteRelationDao, TagNoteRelation> implements TagNoteRelationService {
}
