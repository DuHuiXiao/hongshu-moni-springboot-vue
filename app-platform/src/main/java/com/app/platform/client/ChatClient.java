package com.app.platform.client;

import com.app.platform.config.FeignRequestInterceptor;
import com.app.common.im.Message;
import com.app.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 解决远程调用设置token
 */
@FeignClient(value = "im",url = "http://localhost:8802",configuration = {FeignRequestInterceptor.class})
@Component
public interface ChatClient {
    /**
     * 发送消息
     *
     * @param message 消息实体
     * @return success
     */
    @PostMapping("/im/chat/sendMsg")
    Result<?> sendMsg(@RequestBody Message message);
}
