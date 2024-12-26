package com.app.auth.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.app.auth.constant.AuthConstant;
import com.app.auth.dto.AuthUserDTO;
import com.app.auth.service.AuthUserService;
import com.app.common.result.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.app.common.constant.TokenConstant;
import com.app.common.exception.AppException;
import com.app.common.utils.ConvertUtils;
import com.app.common.utils.JwtUtils;
import com.app.common.utils.RedisUtils;
import com.app.xo.dao.UserDao;
import com.app.xo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;



@Service
@Slf4j
public class AuthUserServiceImpl extends ServiceImpl<UserDao, User> implements AuthUserService {

    @Autowired
    RedisUtils redisUtils;

    private void setUserInfoAndToken(Map<String, Object> map, User authUser) {
        String accessToken = JwtUtils.getJwtToken(authUser.getId(), AuthConstant.ACCESS_TOKEN_EXPIRATION_TIME);
        String refreshToken = JwtUtils.getJwtToken(authUser.getId(), AuthConstant.REFRESH_TOKEN_EXPIRATION_TIME);
        //缓存当前登录用户 refreshToken 创建的起始时间，这个会在刷新accessToken方法中 判断是否要重新生成(刷新)refreshToken时用到
        redisUtils.setEx(AuthConstant.REFRESH_TOKEN_START_TIME + authUser.getId(), String.valueOf(System.currentTimeMillis()), AuthConstant.REFRESH_TOKEN_EXPIRATION_TIME, TimeUnit.MILLISECONDS);
        //将用户信息保存在redis中
        redisUtils.set(AuthConstant.USER_KEY + authUser.getId(), JSONUtil.toJsonStr(authUser));
        map.put(TokenConstant.ACCESS_TOKEN, accessToken);
        map.put(TokenConstant.REFRESH_TOKEN, refreshToken);
        map.put(AuthConstant.USER_INFO, authUser);
    }

    @Override
    public Map<String, Object> login(AuthUserDTO authUserDTO) {
        Map<String, Object> map = new HashMap<>(2);
        String title = authUserDTO.getUsername();
        //查询当前用户
        User authUser = this.getOne(new QueryWrapper<User>().eq("username", title).or().eq("phone", title).or().eq("email", title));

        String s = SecureUtil.md5(authUserDTO.getPassword());
        if (ObjectUtil.isEmpty(authUser) || !s.equals(authUser.getPassword())) {
            throw new AppException(AuthConstant.LOGIN_FAIL);
        }
        setUserInfoAndToken(map, authUser);
        return map;
    }

    //验证码校验，现在暂时不用
    private boolean checkCode(AuthUserDTO authUserDTO) {
//        String code = "";
//        if (StringUtils.isNotBlank(redisUtils.get(AuthConstant.CODE + authUserDTO.getPhone()))) {
//            code = redisUtils.get(AuthConstant.CODE + authUserDTO.getPhone());
//        } else if (StringUtils.isNotBlank(redisUtils.get(AuthConstant.CODE + authUserDTO.getEmail()))) {
//            code = redisUtils.get(AuthConstant.CODE + authUserDTO.getEmail());
//        }
//        return StringUtils.isBlank(code) || !code.equals(authUserDTO.getCode());
        return false;
    }

    @Override
    public Map<String, Object> loginByCode(AuthUserDTO authUserDTO) {
        Map<String, Object> map = new HashMap<>(2);
        User currentUser;
        if (StringUtils.isNotBlank(authUserDTO.getPhone())) {
            currentUser = this.getOne(new QueryWrapper<User>().eq("phone", authUserDTO.getPhone()));
        } else {
            currentUser = this.getOne(new QueryWrapper<User>().eq("email", authUserDTO.getEmail()));
        }
        if (checkCode(authUserDTO) || currentUser == null) {
            throw new AppException(AuthConstant.LOGIN_FAIL);
        }
        setUserInfoAndToken(map, currentUser);
        return map;
    }

    @Override
    public User getUserInfoByToken(String accessToken) {
        String userId = JwtUtils.getUserId(accessToken);
        return this.getById(userId);
    }

    /**
     * 用户注册功能实现
     * 此方法处理用户注册请求，检查用户是否已存在，生成新用户信息，并保存到数据库
     * 同时，为新用户生成随机用户名、密码和唯一标识，并设置默认头像和用户封面
     *
     * @param authUserDTO 包含用户注册信息的数据传输对象
     * @return 返回一个包含注册结果或错误信息的映射
     */
    @Override
    @Transactional
    public Map<String, Object> register(AuthUserDTO authUserDTO) {
        // 初始化一个用于返回结果的映射
        Map<String, Object> map = new HashMap<>(2);

        // 检查电话或邮箱是否已注册
        User currentUser = this.getOne(new QueryWrapper<User>().eq("phone", authUserDTO.getPhone()).or().eq("email", authUserDTO.getEmail()));
        // 如果用户已存在或验证码验证失败，则直接返回空映射
        if (currentUser != null || checkCode(authUserDTO)) {
            return map;
        }

        String id = RandomUtil.randomString(50);

        // 将DTO中的用户信息转换为User实体对象
        User user = ConvertUtils.sourceToTarget(authUserDTO, User.class);
        // 为新用户生成随机的ID，营销系统ID、用户名、默认头像、用户封面和加密后的默认密码
        user.setId(id);
        user.setYxId(Long.valueOf(RandomUtil.randomNumbers(10)));
        user.setAvatar(AuthConstant.DEFAULT_AVATAR);
        user.setUserCover(AuthConstant.DEFAULT_COVER);
        String password = SecureUtil.md5(AuthConstant.DEFAULT_PASSWORD);
        user.setPassword(password);

        // 保存新用户到数据库
        this.save(user);

        // 设置用户信息和令牌，并将其添加到返回的映射中
        setUserInfoAndToken(map, user);

        // 返回包含用户信息和令牌的映射
        return map;
    }


    @Override
    public boolean isRegister(AuthUserDTO authUserDTO) {
        long count = this.count(new QueryWrapper<User>().eq("phone", authUserDTO.getPhone()).or().eq("email", authUserDTO.getEmail()));
        return count > 0;
    }

    @Override
    public void loginOut(String userId) {
        String userKey = AuthConstant.USER_KEY + userId;
        String refreshTokenStartTimeKey = AuthConstant.REFRESH_TOKEN_START_TIME + userId;
        List<String> keyList = new ArrayList<>();
        keyList.add(userKey);
        keyList.add(refreshTokenStartTimeKey);
        redisUtils.delete(keyList);
    }

    @Override
    public boolean updatePassword(AuthUserDTO authUserDTO) {
        if (!authUserDTO.getPassword().equals(authUserDTO.getCheckPassword())) {
            return false;
        }
        String pwd = SecureUtil.md5(authUserDTO.getPassword());
        User user;
        if (StringUtils.isBlank(authUserDTO.getId())) {
            user = this.getOne(new QueryWrapper<User>().eq("phone", authUserDTO.getPhone()).or().eq("email", authUserDTO.getEmail()));
        } else {
            user = this.getById(authUserDTO.getId());
        }
        user.setPassword(pwd);
        return this.updateById(user);
    }

    @Override
    public Map<String, Object> refreshToken(String refreshToken) {
        HashMap<String, Object> res = new HashMap<>(2);
        //刷新accessToken:生成新的accessToken
        String userId = JwtUtils.getUserId(refreshToken);

        log.debug("userId:{}", userId);
        //创建新的accessToken
        String accessToken = JwtUtils.getJwtToken(userId, AuthConstant.ACCESS_TOKEN_EXPIRATION_TIME);

        //minTimeOfRefreshToken  最短刷新时间
        //refreshTokenStartTime  刷新令牌开始时间

        //下面判断是否刷新 refreshToken，如果refreshToken 快过期了 需要重新生成一个替换掉
        //refreshToken 有效时长是应该为accessToken有效时长的2倍
        long minTimeOfRefreshToken = 2 * AuthConstant.ACCESS_TOKEN_EXPIRATION_TIME;
        String refreshTokenStr = redisUtils.get(AuthConstant.REFRESH_TOKEN_START_TIME + userId);

        if (StringUtils.isBlank(refreshTokenStr)) {
            return res;
        }

        //refreshToken创建的起始时间点
        long refreshTokenStartTime = Long.parseLong(refreshTokenStr);
        //(refreshToken上次创建的时间点 + refreshToken的有效时长 - 当前时间点) 表示refreshToken还剩余的有效时长，如果小于2倍accessToken时长 ，则刷新 refreshToken
        if (refreshTokenStartTime + AuthConstant.REFRESH_TOKEN_EXPIRATION_TIME - System.currentTimeMillis() <= minTimeOfRefreshToken) {
            //刷新refreshToken
            refreshToken = JwtUtils.getJwtToken(userId, refreshTokenStartTime);
            redisUtils.setEx(AuthConstant.REFRESH_TOKEN_START_TIME + userId, String.valueOf(System.currentTimeMillis()), AuthConstant.REFRESH_TOKEN_EXPIRATION_TIME, TimeUnit.MILLISECONDS);
        }

        res.put(TokenConstant.ACCESS_TOKEN, accessToken);
        res.put(TokenConstant.REFRESH_TOKEN, refreshToken);
        return res;
    }

}
