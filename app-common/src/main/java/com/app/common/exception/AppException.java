package com.app.common.exception;

import com.app.common.result.ResultCodeEnum;
import com.app.common.utils.MessageUtils;
import lombok.Data;

@Data
public class AppException extends RuntimeException {

    //异常状态码
    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     *
     * @param message
     * @param code
     */
    public AppException(String message, Integer code) {
        super(message);
        this.code = code;
    }


    public AppException(int code, String... params) {
        super(MessageUtils.getMessage(code, params));
        this.code = code;
    }

    public AppException(String msg) {
        super(msg);
        this.code = ResultCodeEnum.FAIL.getCode();
    }

    /**
     * 接收枚举类型对象
     *
     * @param resultCodeEnum
     */
    public AppException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
}
