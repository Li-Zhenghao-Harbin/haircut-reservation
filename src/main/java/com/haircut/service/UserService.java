package com.haircut.service;

import com.haircut.error.BusinessException;
import com.haircut.service.model.UserModel;

public interface UserService {
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
    UserModel validateLogin(String telephone, String password) throws BusinessException;
}
