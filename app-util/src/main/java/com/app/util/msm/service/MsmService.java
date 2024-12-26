package com.app.util.msm.service;


public interface MsmService {

    /**
     * 发送短信
     * @param phone
     * @return
     * @throws Exception
     */
    String sendMsm(String phone) throws Exception;
}
