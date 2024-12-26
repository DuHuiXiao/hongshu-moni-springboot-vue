package com.app.im.factory;

import cn.hutool.json.JSONUtil;
import com.app.im.entity.CountMessage;
import com.app.common.constant.ImConstant;
import com.app.common.im.Message;
import com.app.common.utils.RedisUtils;

public class ChatCountMessage implements MessageFactory{

    RedisUtils redisUtils;

    public ChatCountMessage(RedisUtils redisUtils){
        this.redisUtils = redisUtils;
    }
    @Override
    public void sendMessage(Message message) {
        String messageCountKey = ImConstant.MESSAGE_COUNT_KEY + message.getAcceptUid();
        CountMessage countMessage = JSONUtil.toBean(JSONUtil.toJsonStr(message.getContent()), CountMessage.class);
        countMessage.setUid(message.getAcceptUid());
        redisUtils.set(messageCountKey, JSONUtil.toJsonStr(countMessage));
    }
}
