package com.app.platform.service.impl;


import com.app.platform.service.CommentSyncService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.app.xo.dao.CommentSyncDao;
import com.app.xo.entity.CommentSync;
import org.springframework.stereotype.Service;


@Service
public class CommentSyncServiceImpl extends ServiceImpl<CommentSyncDao, CommentSync> implements CommentSyncService {
}
