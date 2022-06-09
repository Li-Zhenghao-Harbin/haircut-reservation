package com.haircut.error;

import com.haircut.responce.CommonReturnType;

public interface CommonError {
    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);
}
