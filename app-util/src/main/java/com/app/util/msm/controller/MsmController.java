package com.app.util.msm.controller;


import com.app.util.constant.UserConstant;
import com.app.util.dto.UserDTO;
import com.app.common.utils.RedisUtils;
import com.app.util.msm.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/util/msm")
public class MsmController {


    @Autowired
    MsmService msmService;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 发送短信
     *
     * @param
     * @throws Exception
     */
    @PostMapping("sendMsm")
    public void sendMsm(@RequestBody UserDTO userDTO) throws Exception {

        String code = msmService.sendMsm(userDTO.getPhone());
        redisUtils.set(UserConstant.CODE + userDTO.getPhone(), code, 60 * 5L);
    }
}
