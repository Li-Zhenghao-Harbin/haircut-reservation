package com.haircut.controller;

import com.haircut.controller.viewobject.OrderVO;
import com.haircut.controller.viewobject.StaffVO;
import com.haircut.error.BusinessException;
import com.haircut.error.EmBusinessError;
import com.haircut.responce.CommonReturnType;
import com.haircut.service.OrderService;
import com.haircut.service.model.OrderModel;
import com.haircut.service.model.StaffModel;
import com.haircut.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller("order")
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", originPatterns = "*")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/createorder", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name = "staffId") Integer staffId) throws BusinessException {
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        //获取用户登录信息
        UserModel userModel = (UserModel)httpServletRequest.getSession().getAttribute("LOGIN_USER");
        OrderModel orderModel = orderService.createOrder(userModel.getId(), staffId);
        return CommonReturnType.create(orderModel);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listOrder() {
        List<OrderModel> orderModelList = orderService.listOrder();
        List<OrderVO> orderVOList = orderModelList.stream().map(orderModel -> {
            OrderVO orderVO = this.convertVOFromModel(orderModel);
            return orderVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(orderVOList);
    }

    private OrderVO convertVOFromModel(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orderModel, orderVO);
        return orderVO;
    }
}
