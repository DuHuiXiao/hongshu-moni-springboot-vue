package com.app.xo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.app.common.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("t_user")
public class User extends BaseEntity {

    private Long yxId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 电话
     */
    private String phone;
    /**
     * email
     */
    private String email;
    /**
     * 描述
     */
    private String description;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 地址
     */
    private String address;
    /**
     * 封面
     */
    private String userCover;


    private Long trendCount;

    private Long followerCount;

    private Long fanCount;
}
