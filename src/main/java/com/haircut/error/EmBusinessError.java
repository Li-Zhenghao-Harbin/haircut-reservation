package com.haircut.error;

import com.haircut.responce.CommonReturnType;

public enum EmBusinessError implements  CommonError {
    //通用
    UNKNOWN_ERROR(10000, "未知错误"),
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),
    //用户
    USER_NOT_EXIST(20001, "用户不存在"),
    USER_LOGIN_FAIL(20002, "用户手机号或密码不正确"),
    USER_NOT_LOGIN(20003, "用户未登录"),
    //交易
    STOCK_NOT_ENOUGH(30001, "剩余预约名额不足")
    ;

    private EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;
    private String errMsg;

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
