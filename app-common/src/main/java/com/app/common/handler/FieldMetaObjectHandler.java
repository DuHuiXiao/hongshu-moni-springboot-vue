package com.app.common.handler;

import com.app.common.constant.UserConstant;
import com.app.common.utils.WebUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 公共字段，自动填充值
 *
 */
@Component
public class FieldMetaObjectHandler implements MetaObjectHandler {
    private final static String CREATE_DATE = "createDate";
    private final static String CREATOR = "creator";
    private final static String UPDATE_DATE = "updateDate";
    private final static String UPDATER = "updater";

    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        //创建者
        String userId = WebUtils.getRequestHeader(UserConstant.USER_ID);
        strictInsertFill(metaObject, CREATOR, String.class, userId);
        //创建时间
        strictInsertFill(metaObject, CREATE_DATE, Date.class, date);


        //更新者
        strictInsertFill(metaObject, UPDATER, String.class, userId);
        //更新时间
        strictInsertFill(metaObject, UPDATE_DATE, Date.class, date);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String userId = WebUtils.getRequestHeader(UserConstant.USER_ID);
        //更新者
        strictUpdateFill(metaObject, UPDATER, String.class, userId);
        //更新时间
        strictUpdateFill(metaObject, UPDATE_DATE, Date.class, new Date());
    }
}