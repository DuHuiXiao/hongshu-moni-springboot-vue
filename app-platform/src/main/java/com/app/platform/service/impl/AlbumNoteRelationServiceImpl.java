package com.app.platform.service.impl;

import com.app.platform.service.NoteService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.app.common.auth.AuthContextHolder;
import com.app.common.utils.ConvertUtils;
import com.app.platform.service.AlbumNoteRelationService;
import com.app.platform.service.UserService;
import com.app.xo.dao.AlbumNoteRelationDao;
import com.app.xo.entity.AlbumNoteRelation;
import com.app.xo.entity.Note;
import com.app.xo.entity.User;
import com.app.xo.vo.NoteSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AlbumNoteRelationServiceImpl extends ServiceImpl<AlbumNoteRelationDao, AlbumNoteRelation> implements AlbumNoteRelationService {

    @Autowired
    NoteService noteService;

    @Autowired
    UserService userService;

    @Override
    public Page<NoteSearchVo> getNotePageByAlbumId(long currentPage, long pageSize, String albumId, String userId) {
        Page<NoteSearchVo> result = new Page<>();
        Page<AlbumNoteRelation> albumImgRelationPage = this.page(new Page<>(currentPage, pageSize), new QueryWrapper<AlbumNoteRelation>().eq("aid", albumId).orderByDesc("create_date"));
        long total = albumImgRelationPage.getTotal();
        List<AlbumNoteRelation> records = albumImgRelationPage.getRecords();
        List<String> nids = records.stream().map(AlbumNoteRelation::getNid).collect(Collectors.toList());
        String currentUser = AuthContextHolder.getUserId();
        List<Note> noteList;
        if (currentUser.equals(userId)) {
            //表示是当前用户(能够查看所有的专辑，包括上传中的笔记)
            noteList = noteService.listByIds(nids);
        } else {
            noteList = noteService.list(new QueryWrapper<Note>().in("id", nids).eq("status", 1));
        }
        List<String> uids = noteList.stream().map(Note::getUid).collect(Collectors.toList());
        List<User> userList = userService.listByIds(uids);
        HashMap<String, User> userMap = new HashMap<>(16);
        userList.forEach(item -> {
            userMap.put(item.getId(), item);
        });
        HashMap<String, Note> noteMap = new HashMap<>(16);
        noteList.forEach(item -> {
            noteMap.put(item.getId(), item);
        });
        List<NoteSearchVo> noteVoList = new ArrayList<>();
        Note note;
        User user;
        NoteSearchVo noteSearchVo;
        for (AlbumNoteRelation model : records) {
            note = noteMap.get(model.getNid());
            user = userMap.get(note.getUid());
            noteSearchVo = ConvertUtils.sourceToTarget(note, NoteSearchVo.class);
            noteSearchVo.setUsername(user.getUsername())
                    .setAvatar(user.getAvatar());
            noteVoList.add(noteSearchVo);
        }
        result.setTotal(total);
        result.setRecords(noteVoList);
        return result;
    }
}
