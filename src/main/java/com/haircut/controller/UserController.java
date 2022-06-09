package com.haircut.controller;

import com.alibaba.druid.util.StringUtils;
import com.haircut.controller.viewobject.UserVO;
import com.haircut.error.BusinessException;
import com.haircut.error.EmBusinessError;
import com.haircut.responce.CommonReturnType;
import com.haircut.service.UserService;
import com.haircut.service.model.UserModel;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", originPatterns = "*")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //用户登录接口
    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "telephone") String telephone,
                                  @RequestParam(name = "password") String password) throws BusinessException {
        if (org.apache.commons.lang3.StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //用户登录服务，校验用户登录是否合法
        UserModel userModel = userService.validateLogin(telephone, password);
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);
        UserVO userVO = convertFromModel(userModel);
        return CommonReturnType.create(userVO);
    }

//    //用户获取OTP
//    @RequestMapping("/getotp")
//    @ResponseBody
//    public CommonReturnType getOtp(@RequestParam(name = "telephone") String telephone) {
//        Random random = new Random();
//        int randomInt = random.nextInt(99999);
//        randomInt += 10000;
//        String otpCode = String.valueOf(randomInt);
//        //绑定
//        httpServletRequest.getSession().setAttribute(telephone, otpCode);
//        System.out.println("telephone = " + telephone + ", otpCode = " + otpCode);
//        return CommonReturnType.create(null);
//    }

    //用户注册接口
    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "name") String name,
                                     @RequestParam(name = "telephone") String telephone,
                                     @RequestParam(name = "password") String password) throws BusinessException {
        //用户注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setTelephone(telephone);
        userModel.setPassword(password);
        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/request", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType request() throws BusinessException {
//        httpServletResponse.setHeader("Set-Cookie", "JSESSIONID=xxx;SameSite=None;Secure");
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        UserVO userVO = convertFromModel(userModel);
        return CommonReturnType.create(userVO);
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        //调用service服务获取对应id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        UserVO userVO = convertFromModel(userModel);
        //返回通用对象
        return CommonReturnType.create(userVO);
    }

    private UserVO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }
}
