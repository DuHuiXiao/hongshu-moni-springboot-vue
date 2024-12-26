package com.app.auth.constant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("权限管理常量")
public interface AuthConstant {

   @ApiModelProperty("accessToken过期时间")
   long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24;
   @ApiModelProperty("refreshToken过期时间")
   long REFRESH_TOKEN_EXPIRATION_TIME = ACCESS_TOKEN_EXPIRATION_TIME * 2;
   @ApiModelProperty("refreshToken保留时间")
   String REFRESH_TOKEN_START_TIME = "refreshTokenStartTime:";
   @ApiModelProperty("用户key")
   String USER_KEY = "userKey:";
   @ApiModelProperty("用户信息")
   String USER_INFO = "userInfo";
   @ApiModelProperty("验证码key")
   String CODE = "code:";
   @ApiModelProperty("默认头像")
   String DEFAULT_AVATAR = "https://ooo.0x0.ooo/2024/12/10/OLV7Jv.png";
   @ApiModelProperty("默认背景")
   String DEFAULT_COVER = "https://ooo.0x0.ooo/2024/12/10/OLV1xY.png";
   @ApiModelProperty("默认密码")
   String DEFAULT_PASSWORD = "123456";
   @ApiModelProperty("登录失败")
   String LOGIN_FAIL = "登录失败";
}
